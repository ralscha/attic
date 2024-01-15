import {Component} from '@angular/core';
import {NavController, ToastController} from 'ionic-angular';
import {PlayerService} from "../../providers/player-service";
import {Player} from "../../providers/player";
import {InputPage} from "../input/input";
import {Track} from "../../providers/track";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  constructor(private readonly navCtrl: NavController,
              public readonly playerService: PlayerService,
              private readonly toastCtrl: ToastController) {
  }

  ionViewDidLoad() {
    this.playerService.fetchPlayers();
  }

  showSpieler(player: Player) {
    const track:Track = {player, ries:0};
    console.log(track);
    this.navCtrl.push(InputPage, {track});
  }

  clear() {
    this.playerService.clear();

    let toast = this.toastCtrl.create({
      message: 'Daten gelöscht',
      duration: 3000,
      position: 'bottom'
    });
    toast.present();

  }

}
