package fr.ciag.planning.ui;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface Ihm {
	int ordre() default 999;
	boolean visible() default true;
	boolean editable() default true;
	String libelle() default "";
	String description() default "";
	String type() default "text";
}
