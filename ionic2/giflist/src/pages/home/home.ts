import {Component} from '@angular/core';
import {NavController, ModalController, Platform} from 'ionic-angular';
import {Data} from "../../providers/data";
import {Reddit} from "../../providers/reddit";
import {FormControl} from "@angular/forms";
import {Keyboard} from "ionic-native";
import { InAppBrowser } from 'ionic-native';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {SettingsPage} from "../settings/settings";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  subredditValue: string;
  subredditControl: FormControl;

  constructor(public navCtrl: NavController, private readonly dataService: Data,
              public readonly redditService: Reddit, private readonly modalCtrl: ModalController,
              private readonly platform: Platform) {
    this.subredditControl = new FormControl();
  }

  ionViewDidLoad() {
    this.subredditControl.valueChanges.debounceTime(1500)
      .distinctUntilChanged().subscribe(subreddit => {
       if (subreddit != '' && subreddit) {
          this.redditService.subreddit = subreddit;
          this.changeSubreddit();
          Keyboard.close();
       }
    });
    this.platform.ready().then(()=>this.loadSettings());
  }

  loadSettings(): void {

    this.dataService.getData().then((settings) => {

      if(settings && typeof(settings) != "undefined"){

        let newSettings = JSON.parse(settings);
        this.redditService.settings = newSettings;

        if(newSettings.length != 0){
          this.redditService.sort = newSettings.sort;
          this.redditService.perPage = newSettings.perPage;
          this.redditService.subreddit = newSettings.subreddit;
        }   

      }

      this.changeSubreddit();

    });

  }

  showComments(post): void {
    new InAppBrowser('http://reddit.com' + post.data.permalink, '_system');
  }

  openSettings(): void {
    let settingsModal = this.modalCtrl.create(SettingsPage, {
      perPage: this.redditService.perPage,
      sort: this.redditService.sort,
      subreddit: this.redditService.subreddit
    });

    settingsModal.onDidDismiss(settings => {
      if(settings){
        this.redditService.perPage = settings.perPage;
        this.redditService.sort = settings.sort;
        this.redditService.subreddit = settings.subreddit;

        this.dataService.save(settings);
        this.changeSubreddit();
      }
    });

    settingsModal.present();
  }

  playVideo(video, post): void {

    if(!post.alreadyLoaded){
      post.showLoader = true;
    }

    //Toggle the video playing
    if(video.paused){

      //Show the loader gif
      video.play();

      //Once the video starts playing, remove the loader gif
      video.addEventListener("playing", function(e){
        post.showLoader = false;
        post.alreadyLoaded = true;
      });

    } else {
      video.pause();
    }
  }

  changeSubreddit(): void {
  	this.redditService.resetPosts();
  }

  loadMore(): void {
    this.redditService.nextPage();
  }

}
