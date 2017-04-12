package yt.javi.fithdown.core.application;

public interface Service<R, T> {

  T execute(R request);
}
