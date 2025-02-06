import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

public class AnnotationTest {

    @myAnnotation(RetentionPolicy.RUNTIME)

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface myAnnotation {
    RetentionPolicy value();
}