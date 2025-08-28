import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Log} from './log';
import {format} from 'date-fns';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.scss']
})
export class LogComponent implements OnInit {

  travellog: string | null = null;
  selectedLog: Log | null = null;

  logs: Log[] = [];
  options: google.maps.MapOptions = {
    mapTypeId: 'hybrid',
    maxZoom: 16,
    zoom: 8,
    center: {lat: 51.673858, lng: 7.815982}
  };


  constructor(private readonly httpClient: HttpClient,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];

    this.httpClient.get(`/be/logview_name/${id}`, {responseType: 'text'}).subscribe(response => this.travellog = response);
    this.httpClient.get<Log[]>(`/be/logview/${id}`).subscribe(response => {
      this.logs = response;
      if (this.logs.length > 0) {
        this.selectedLog = this.logs[0];
      }
    });
  }

  onLogClick(log: Log): void {
    console.log(log);
    this.selectedLog = log;
  }

  createdString(log: Log): string {
    return format(log.created * 1000, 'yyyy-MM-dd HH:mm');
  }

}
