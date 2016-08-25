import gui.GuiSimple;

public class BouncingSimple_TooHigh extends GuiSimple {
    private double _ballHeight;
    private double _velocity;

    public void setup() {
        _ballHeight = 50.0;
        _velocity = 0.0;
    }

    public void draw() {
        screen.clear();
        screen.circle(200.0, _ballHeight, 10.0);

        if (_ballHeight > 400.0) {
            _velocity *= -1.0;
        }

        _ballHeight += _velocity;
        _velocity += 0.3;
        System.out.println(_ballHeight + " " + _velocity);
    }

    public static void main(String[] args) {
        begin(new BouncingSimple_TooHigh());
    }
}
