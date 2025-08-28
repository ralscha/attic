import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MessagesService} from '../../service/messages.service';
import {AlertController} from '@ionic/angular';
import {NgForm} from '@angular/forms';
import {TravelService} from '../../service/travel.service';
import {Travel} from '../../model/travel';

@Component({
  selector: 'app-travel-edit',
  templateUrl: './edit.page.html',
  styleUrls: ['./edit.page.scss'],
})
export class TravelEditPage implements OnInit {

  selectedTravel!: Travel;

  constructor(private readonly route: ActivatedRoute,
              private readonly router: Router,
              private readonly messagesService: MessagesService,
              private readonly alertController: AlertController,
              private readonly travelService: TravelService) {
  }

  async ngOnInit(): Promise<void> {
    const travelIdString = this.route.snapshot.paramMap.get('id');
    if (travelIdString) {
      this.selectedTravel = await this.travelService.getEntry(parseInt(travelIdString, 10));
    } else {
      this.selectedTravel = {
        id: -1,
        name: "",
        ts: 0
      };
    }
  }

  async deleteTravel(): Promise<void> {
    if (this.selectedTravel) {
      const alert = await this.alertController.create({
        header: 'Delete Travel',
        message: 'Do you really want to delete this entry?',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel'
          }, {
            text: 'Delete Travel',
            handler: async () => this.reallyDeleteTravel()
          }
        ]
      });
      await alert.present();
    }
  }

  async save(form: NgForm): Promise<void> {
    this.selectedTravel.name = form.value.name;
    await this.travelService.save(this.selectedTravel);
    await this.messagesService.showSuccessToast('Travel successfully saved', 1000);
    await this.router.navigate(['/travel']);
  }

  shareEnabled(): boolean {
    // @ts-ignore
    return this.selectedTravel?.id > 0 && navigator.share;
  }

  async share(): Promise<void> {
    // @ts-ignore
    navigator.share({
      title: 'TravelLog',
      text: this.selectedTravel.name,
      url: 'https://travel.hplar.ch/view/log/' + this.selectedTravel.id
    });
  }

  private async reallyDeleteTravel(): Promise<void> {
    await this.travelService.delete(this.selectedTravel);
    await this.router.navigate(['/travel']);
  }
}
