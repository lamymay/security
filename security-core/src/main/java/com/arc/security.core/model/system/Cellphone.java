package com.arc.security.core.model.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 叶超
 * @since 2019/5/26 21:49
 */
@Setter
@Getter
@ToString
public class Cellphone {

    private Long id;

    private String name;

    //    @NotBlank()
    private Date date;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    public static void main(String[] args) {
        System.err.println("12".equals(12));
        System.err.println("12".equals(String.valueOf(12)));
        System.err.println("12".equals(String.valueOf(12)));
        System.err.println("12".equals(String.valueOf(12)));

        String a = "12";
        String valueOf = String.valueOf(12);
        System.out.println(a);
        System.out.println(valueOf);
        System.out.println("两个对象");
        System.out.println(a == valueOf);
        System.out.println(a.equals(valueOf));

        String e = "129";
        String f = String.valueOf(129);
        System.out.println("-----------------");
        System.out.println(e == f);
        System.out.println(e.equals(f));
        System.out.println("----------------------------------127");
        Integer x = 127;
        Integer y = 127;

        System.out.println(x);
        System.out.println(y);
        System.out.println(x == y);
        System.out.println(x.equals(y));
        System.out.println("================128");
        Integer m = 128;
        Integer n= 128;

        System.out.println(m);
        System.out.println(n);
        System.out.println(m == n);
        System.out.println(m.equals(n));
    }

}
