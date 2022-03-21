package higorn.katas.christmasdelivery;

public interface PresetReceiver {
  void receivePreset(Present present);
  void awaitClosure();
}
