package yt.javi.fithdown.core.application;

public interface Service<R, T> {
  public T execute(R request);
}
