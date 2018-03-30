package models.interfaces;

public interface IOperatable {
    public void translate(double dx, double dy);
    public void rotate(double alpha, double cx, double cy);
    public void rotate(double alpha);
}
