package uz.sh.online_queue.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Regions {
    Andijan("Andijan","Andijon","Андижон"),
    Bukhara("Bukhara","Buxoro","Бухоро"),
    Ferghana("Ferghana","Farg'ona","Фарғона"),
    Jizzakh("Jizzakh","Jizzax","Жиззах"),
    Kharezm("Kharezm","Xorazm","Хоразм"),
    Namangan("Namangan","Namangan","Наманган"),
    Navaiy("Navaiy","Navoiy","Навоий"),
    Kashkadarya("Kashkadarya","Qashqadaryo","Қашқадарё"),
    Karakalpakistan_REPUBLIC("Karakalpakistan republic","Qoraqalpog’iston Respublikasi","Қорақалпоғистон Республикаси"),
    Samarkand("Samarkand","Samarqand","Самарқанд"),
    Sirdarya("Sirdarya","Sirdaryo","Сирдарё"),
    Surkhondarya("Surkhondarya","Surxandaryo","Сурхондарё"),
    TASHKENT("Tashkent","Toshkent","Тaшкент"),
    UNDEFINED("UNDERFINED","Topilmadi","Неопределенный");

    private final String eng;
    private final String uzb;
    private final String rus;

    public static String getRegion(String name){
        for (Regions value : values()) {
            if (value.getEng().equalsIgnoreCase(name)) return value.getEng();
        }
        return UNDEFINED.eng;
    }

    public static List<String> getList(){

        return  Arrays.stream(values()).map(Regions::getEng).toList();
    }



}
