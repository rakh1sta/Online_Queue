package uz.sh.online_queue.repository.mongorepository;

import uz.sh.online_queue.dto.auth.employee.AdminUpdateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.dto.auth.user.UserUpdateDto;
import uz.sh.online_queue.entity.auth.Auth;
import uz.sh.online_queue.repository.AbstractRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MongoTemplateRepository implements AbstractRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoTemplateRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void block(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("blocked", true);
        mongoTemplate.findAndModify(query, update, Auth.class);
    }

    public void unblock(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("blocked", false);
        mongoTemplate.findAndModify(query, update, Auth.class);
    }

    public void softDelete(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("deleted", true);
        mongoTemplate.findAndModify(query, update, Auth.class);
    }

    public void changePassword(ObjectId id, String confirmPassword) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("password",confirmPassword);
        mongoTemplate.findAndModify(query,update,Auth.class);

    }

    public void addRole(String id, String role) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("role",role);
        mongoTemplate.findAndModify(query,update,Auth.class);

    }

    public void updateEmployee(EmployeeUpdateDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(dto.getId()));
        Update update = new Update();
        if(!dto.getFirstName().isBlank()) {
            update.set("firstName",dto.getFirstName());
        }
        if(!dto.getLastName().isBlank()) {
            update.set("lastName",dto.getLastName());
        }
        if(!dto.getPassportSerial().isBlank()) {
            update.set("passportSerial",dto.getPassportSerial());
        }
        if(!dto.getPhoneNumber().isBlank()) {
            update.set("phoneNumber",dto.getPhoneNumber());
        }
        if(Objects.isNull(dto.getExperience())) {
            update.set("experience",dto.getExperience());
        }
        if(!dto.getDepartmentId().isBlank()) {
            update.set("departmentId",dto.getDepartmentId());
        }
        mongoTemplate.findAndModify(query,update,Auth.class);

    }

    public void updateAdmin(AdminUpdateDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(dto.getId()));
        Update update = new Update();
        if(!dto.getFirstName().isBlank()) {
            update.set("firstName",dto.getFirstName());
        }
        if(!dto.getLastName().isBlank()) {
            update.set("lastName",dto.getLastName());
        }
        if(!dto.getPassportSerial().isBlank()) {
            update.set("passportSerial",dto.getPassportSerial());
        }
        if(!dto.getPhoneNumber().isBlank()) {
            update.set("phoneNumber",dto.getPhoneNumber());
        }
        if(Objects.isNull(dto.getExperience())) {
            update.set("experience",dto.getExperience());
        }
        mongoTemplate.findAndModify(query,update,Auth.class);

    }

    public void updateUser(UserUpdateDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(dto.getId()));
        Update update = new Update();
        if(!dto.getFirstName().isBlank()) {
            update.set("firstName",dto.getFirstName());
        }
        if(!dto.getLastName().isBlank()) {
            update.set("lastName",dto.getLastName());
        }
        if(!dto.getPassportSerial().isBlank()) {
            update.set("passportSerial",dto.getPassportSerial());
        }
        if(!dto.getPhoneNumber().isBlank()) {
            update.set("phoneNumber",dto.getPhoneNumber());
        }
        mongoTemplate.findAndModify(query,update, Auth.class);
    }


    public Page<Auth> getAll(String role, Pageable pageable) {
        var query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();

        if (role != null && !role.isBlank())
            criteria.add(Criteria.where("role").regex(role, "i"));

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        query.addCriteria(Criteria.where("deleted").is(false));
        query.addCriteria(Criteria.where("blocked").is(false));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Auth.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Auth.class)
        );
    }

    public Page<Auth> getAllBlocked(String role, Pageable pageable) {
        var query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();

        if (role != null && !role.isBlank())
            criteria.add(Criteria.where("role").regex(role, "i"));

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        query.addCriteria(Criteria.where("blocked").is(true));
        query.addCriteria(Criteria.where("deleted").is(false));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Auth.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Auth.class)
        );
    }

    public Page<Auth> getAllByOrgId(String role, Pageable pageable, String orgId) {
        var query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();

        if (role != null && !role.isBlank())
            criteria.add(Criteria.where("role").regex(role, "i"));

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        query.addCriteria(Criteria.where("organizationId").is(orgId));
        query.addCriteria(Criteria.where("deleted").is(false));
        query.addCriteria(Criteria.where("blocked").is(false));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Auth.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Auth.class)
        );
    }

    public Page<Auth> getAllBlockedByOrgId(String role, Pageable pageable, String orgId) {
        var query = new Query().with(pageable);
        final List<Criteria> criteria = new ArrayList<>();

        if (role != null && !role.isBlank())
            criteria.add(Criteria.where("role").regex(role, "i"));

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        query.addCriteria(Criteria.where("blocked").is(true));
        query.addCriteria(Criteria.where("organizationId").is(orgId));
        query.addCriteria(Criteria.where("deleted").is(false));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Auth.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Auth.class)
        );
    }
}
