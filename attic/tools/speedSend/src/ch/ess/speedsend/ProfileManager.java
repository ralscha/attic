package ch.ess.speedsend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

public class ProfileManager {
  private Map<String, ProfileItem> profiles = new TreeMap<String, ProfileItem>();

  public Map<String, ProfileItem> getProfiles() {
    return profiles;
  }

  public void setProfiles(Map<String, ProfileItem> profiles) {
    this.profiles = profiles;
  }
  
  public void add(String name, ProfileItem item) {
    profiles.put(name, item);
  }
  
  public void remove(String name) {
    profiles.remove(name);
  }
  
  public ProfileItem getProfile(String name) {
    return profiles.get(name);
  }
  
  @SuppressWarnings("unchecked")
  public void load(String fileName) throws IOException, ClassNotFoundException {
    
    File profile = new File(fileName);
    if (profile.exists()) {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(profile));
      profiles = (Map<String, ProfileItem>)ois.readObject();
      profiles = new TreeMap<String, ProfileItem>(profiles);
      ois.close();
    }
    
  }
  
  public void save(String fileName) throws IOException {
    File profile = new File(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(profile));
    oos.writeObject(profiles);
    oos.close();
  }
  
}
