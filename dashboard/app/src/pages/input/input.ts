import {Component} from '@angular/core';
import {NavParams} from 'ionic-angular';
import {Track} from "../../providers/track";
import {PlayerService} from "../../providers/player-service";

@Component({
  selector: 'page-input',
  templateUrl: 'input.html'
})
export class InputPage {

  track: Track;
  punkte: string = '';
  no: boolean;

  constructor(public navParams: NavParams,
              public readonly playerService: PlayerService) {
  }

  ionViewWillEnter() {
    this.track = this.navParams.get("track");
    this.updateVariables();
  }

  swipeEvent(event) {
    if (event.direction == 2) {
      if (this.playerService.hasNext(this.track)) {
        this.next();
      }
    }
    else {
      if (this.playerService.hasPrev(this.track)) {
        this.back();
      }
    }
  }

  addPunkte(punkt) {
    this.punkte += punkt;
    this.track.player.ries[this.track.ries] = parseInt(this.punkte);
    this.playerService.postPunkte(this.track);
  }

  back() {
    this.track = this.playerService.back(this.track);
    this.updateVariables();
  }

  next() {
    this.track = this.playerService.next(this.track);
    this.updateVariables();
  }

  private updateVariables() {
    this.no = this.track.player.no[this.track.ries];
    const rs = this.track.player.ries[this.track.ries];
    if (rs) {
      this.punkte = String(this.track.player.ries[this.track.ries]);
    }
    else {
      this.punkte = '';
    }
  }

  noClick() {
    this.no = !this.no;
    this.track.player.no[this.track.ries] = this.no;
    this.playerService.postPunkte(this.track);
  }

  reset() {
    this.punkte = '';
    this.no = false;

    this.track.player.ries[this.track.ries] = null;
    this.track.player.no[this.track.ries] = this.no;
    this.playerService.postPunkte(this.track);
  }

}
