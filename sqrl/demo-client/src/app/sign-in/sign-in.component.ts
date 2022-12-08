import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {MessageService} from 'primeng/api';
import {take} from 'rxjs/operators';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import * as qrcode from 'qrcode-generator';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  qrLink: SafeResourceUrl;
  qrCode: string;
  eventSource: EventSource;

  constructor(private readonly router: Router,
              private readonly httpClient: HttpClient,
              private readonly authService: AuthService,
              private readonly messageService: MessageService,
              readonly domSanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    // is the user already authenticated
    this.authService.authentication$.pipe(take(1)).subscribe(authenticated => {
      if (authenticated) {
        this.router.navigate(['home'], {replaceUrl: true});
      }
    });

    this.httpClient.get<{ nut: string, pollingNut: string }>('sqrl-nut').subscribe(nuts => {
      const link = `sqrl://${document.location.host}/sqrl?nut=${nuts.nut}`;
      this.qrLink = this.domSanitizer.bypassSecurityTrustResourceUrl(link);
      const qr = qrcode(0, 'L');
      qr.addData(link);
      qr.make();
      this.qrCode = qr.createDataURL(4);

      this.eventSource = new EventSource(`sqrl-polling/${nuts.pollingNut}`);
      this.eventSource.addEventListener('message', response => {
        console.log(response);
      }, false);
    });

  }

}
