package ch.ralscha.springwicket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ch.ralscha.springwicket.domain.Lokationen;

@Service
public class LokationenService extends GenericDaoService<Lokationen, Integer> {

  @Transactional
  public void insert(String name, int adresse, int artId, String text) {
    Lokationen lokation = new Lokationen();
    lokation.setName(name);
    lokation.setAdresse(adresse);
    lokation.setArtId(artId);
    lokation.setText(text);
    persist(lokation);
  }

}
