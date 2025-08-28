import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MessagesService} from '../../service/messages.service';
import {AlertController} from '@ionic/angular';
import {NgForm} from '@angular/forms';
import {Log} from '../../model/log';
import {LogService} from '../../service/log.service';
import {format} from 'date-fns';
import {parse} from 'date-fns';

@Component({
  selector: 'app-log-edit',
  templateUrl: './edit.page.html',
  styleUrls: ['./edit.page.scss'],
})
export class LogEditPage implements OnInit {
  online = true;
  selectedLog!: Log;
  createdString!: string;

  constructor(private readonly route: ActivatedRoute,
              private readonly router: Router,
              private readonly messagesService: MessagesService,
              private readonly alertController: AlertController,
              private readonly logService: LogService) {
  }

  async ngOnInit(): Promise<void> {
    const logIdString = this.route.snapshot.paramMap.get('id');
    if (logIdString) {
      this.selectedLog = await this.logService.getEntry(parseInt(logIdString, 10));
      this.createdString = format(this.selectedLog.created * 1000, 'yyyy-MM-dd HH:mm');
    } else {
      this.createdString = format(new Date(), 'yyyy-MM-dd HH:mm');
      this.selectedLog = {
        id: null,
        created: null,
        location: null,
        report: null,
        lat: null,
        lng: null,
        travelId: this.logService.getTravelId(),
        ts: 0
      };
      navigator.geolocation.getCurrentPosition(pos => {
        this.selectedLog.lat = pos.coords.latitude;
        this.selectedLog.lng = pos.coords.longitude;
      });
    }
  }

  async deleteLog(): Promise<void> {
    if (this.selectedLog) {
      const alert = await this.alertController.create({
        header: 'Delete Log',
        message: 'Do you really want to delete this entry?',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel'
          }, {
            text: 'Delete Log',
            handler: async () => this.reallyDeleteLog()
          }
        ]
      });
      await alert.present();
    }
  }

  async save(form: NgForm): Promise<void> {
    const createdDate = parse(form.value.time, 'yyyy-MM-dd HH:mm', new Date());

    this.selectedLog.created = createdDate.getTime() / 1000;
    this.selectedLog.location = form.value.location;
    this.selectedLog.report = form.value.report;
    this.selectedLog.lat = form.value.lat;
    this.selectedLog.lng = form.value.lng;
    this.selectedLog.travelId = this.logService.getTravelId();

    await this.logService.save(this.selectedLog);
    await this.messagesService.showSuccessToast('Travel successfully saved', 1000);
    await this.router.navigate(['/log']);
  }

  refreshLocation(): void {
    navigator.geolocation.getCurrentPosition(pos => {
      this.selectedLog.lat = pos.coords.latitude;
      this.selectedLog.lng = pos.coords.longitude;
    });
  }

  private async reallyDeleteLog(): Promise<void> {
    await this.logService.delete(this.selectedLog);
    await this.router.navigate(['/log']);
  }
}
