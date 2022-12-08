import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {FormsModule} from '@angular/forms';
import {CustomFormsModule} from 'ngx-custom-validators';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoadingBarHttpClientModule} from '@ngx-loading-bar/http-client';
import {LoadingBarModule} from '@ngx-loading-bar/core';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SignInComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    InputTextModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LoadingBarHttpClientModule,
    LoadingBarModule,
    ButtonModule,
    ToastModule,
    CustomFormsModule,
    AppRoutingModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
