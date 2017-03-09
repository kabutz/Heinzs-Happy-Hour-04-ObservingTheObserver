package java.util;

class StubException extends UnsupportedOperationException {
}

public class Observable<
    S extends Observable<S, O, A>,
    O extends Observer<S, O, A>,
    A> {
  public Observable() {
    throw new StubException();
  }

  public void addObserver(O o) {
    throw new StubException();
  }

  public void deleteObserver(O o) {
    throw new StubException();
  }

  public void notifyObservers(A arg) {
    throw new StubException();
  }

  public void notifyObservers() {
    throw new StubException();
  }

  public void deleteObservers() {
    throw new StubException();
  }

  protected void setChanged() {
    throw new StubException();
  }

  protected void clearChanged() {
    throw new StubException();
  }

  public boolean hasChanged() {
    throw new StubException();
  }

  public int countObservers() {
    throw new StubException();
  }
}