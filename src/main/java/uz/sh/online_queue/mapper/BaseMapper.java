package uz.sh.online_queue.mapper;

import java.util.List;

public interface BaseMapper<E, D, CD, UD> extends Mapper {

    D toDto(E e);

    List<D> toDto(List<E> e);

    E fromCreateDto(CD cd);

    E fromUpdateDto(UD d);
}
