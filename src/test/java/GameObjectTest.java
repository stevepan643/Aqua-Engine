import com.steve.graphic.GameObject;

public class GameObjectTest {
  public static void main(String[] args) {
    GameObject object = new GameObject("test");
    object.new Model("model1", null, null, null);

    object.getModel("model1");
  }
}
