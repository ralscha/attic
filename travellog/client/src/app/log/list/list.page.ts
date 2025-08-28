import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {LogService} from '../../service/log.service';
import {Log} from '../../model/log';
import {TravelService} from '../../service/travel.service';
import {format} from 'date-fns';

@Component({
  selector: 'app-log-list',
  templateUrl: './list.page.html',
  styleUrls: ['./list.page.scss'],
})
export class LogListPage implements OnInit {

  logs$: Observable<Log[]>;
  selectedTravel: string;

  constructor(private readonly logService: LogService,
              private readonly travelService: TravelService) {
  }

  async ngOnInit(): Promise<void> {
    this.selectedTravel = await this.travelService.getDefaultTravelName();
    const selectedTravelId = await this.travelService.getDefaultTravelId();
    if (selectedTravelId) {
      this.logService.setTravelId(selectedTravelId);
      this.logs$ = this.logService.getObservable();
      this.logService.requestSync();
    }
  }

  refresh(event: any): void {
    this.logService.requestSync()
      .finally(() => event.target.complete());
  }

  createdString(log: Log): string {
    return format(log.created * 1000, 'yyyy-MM-dd HH:mm');
  }

}
