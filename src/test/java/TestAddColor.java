import com.steve.graphic.Mesh;

public class TestAddColor {
    public static void main(String[] args) {
        Mesh.createMeshNonColor(new float[] {
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        }, new int[] {
                11, 22, 33,
                44, 55, 66
        });
    }
}