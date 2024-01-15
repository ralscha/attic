import { NavParams, ViewController } from 'ionic-angular';
import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'page-slideshow',
  templateUrl: 'slideshow.html'
})
export class SlideshowPage {

  @ViewChild('imagePlayer') imagePlayer: ElementRef;

  imagePlayerInterval: any;
  photos: any;

  constructor(public navParams: NavParams, public viewCtrl: ViewController) {
    this.photos = this.navParams.get('photos');
  }

  ionViewDidEnter(){
    this.playPhotos();
  }

  closeModal(){
  	this.viewCtrl.dismiss();
  }

  playPhotos(){

    let imagePlayer = this.imagePlayer.nativeElement;
    let i = 0;

    //Clear any interval already set
    clearInterval(this.imagePlayerInterval);

    //Restart
    this.imagePlayerInterval = setInterval(() => {
      if(i < this.photos.length){
        imagePlayer.src = this.photos[i].image;
        i++;
      }
      else {
        clearInterval(this.imagePlayerInterval);
      }
    }, 500);  	
  }

}