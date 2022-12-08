import {Component} from '@angular/core';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  authenticated = false;

  constructor(private readonly router: Router,
              private readonly authService: AuthService) {
    this.authService.authentication$.subscribe(authenticated => this.authenticated = authenticated);
  }

  signout() {
    this.authService.signout().subscribe(() => {
      this.authenticated = false;
      this.router.navigate(['signin'], {replaceUrl: true});
    });
  }
}
