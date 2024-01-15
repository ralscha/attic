import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';

@Pipe({
  name: 'sanitiser'
})
@Injectable()
export class Sanitiser implements PipeTransform {

  constructor(public _sanitise: DomSanitizer) {
  }

  transform(v: string) {
    return this._sanitise.bypassSecurityTrustHtml(v);
  }

}

