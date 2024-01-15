import { Injectable, Pipe } from '@angular/core';

@Pipe({
  name: 'daysAgo'
})
@Injectable()
export class DaysAgo {
  transform(value, args?) {

    let now = new Date();
    let oneDay = 24 * 60 * 60 * 1000;
    let diffDays = Math.round(Math.abs((value.getTime() - now.getTime())/(oneDay)));

    return diffDays;
  }
}