import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {User} from '../model/user';
import {HttpClient} from '@angular/common/http';
import {filter, map, mergeMap, shareReplay, tap, toArray} from 'rxjs/operators';
import {IonItemSliding} from '@ionic/angular';

@Component({
  selector: 'app-users',
  templateUrl: './users.page.html',
  styleUrls: ['./users.page.scss'],
})
export class UsersPage implements OnInit {

  users$!: Observable<User[]>;
  private allUsers$!: Observable<User[]>;
  private httpGetUsers: Observable<User[]> = this.httpClient.get<User[]>('/be/admin/users');
  private searchFilter: string | null = null;
  private selectFilter: string | null = null;

  constructor(private readonly httpClient: HttpClient) {
  }

  ngOnInit(): void {
    this.allUsers$ = this.httpGetUsers.pipe(shareReplay());
    this.users$ = this.allUsers$;
  }

  refresh(event: any): void {
    this.allUsers$ = this.httpGetUsers.pipe(tap(() => event.target.complete()), shareReplay());
    this.doFilter();
  }

  activate(user: User, slidingItem: IonItemSliding): void {
    slidingItem.close();
    this.httpClient.post<void>('/be/admin/activate', user.id)
      .subscribe(() => user.expired = false);
  }

  disable(user: User, slidingItem: IonItemSliding): void {
    slidingItem.close();
    this.httpClient.post<void>('/be/admin/disable', user.id)
      .subscribe(() => user.enabled = false);
  }

  enable(user: User, slidingItem: IonItemSliding): void {
    slidingItem.close();
    this.httpClient.post<void>('/be/admin/enable', user.id)
      .subscribe(() => user.enabled = true);
  }

  delete(user: User, slidingItem: IonItemSliding): void {
    slidingItem.close();
    this.httpClient.post<void>('/be/admin/delete', user.id)
      .subscribe(() => {
        this.users$ = this.allUsers$.pipe(map(users => users.filter(u => u.id !== user.id)));
      });
  }

  onSearch(event: any): void {
    this.searchFilter = event.target.value;
    this.doFilter();
  }

  onFilterChange(event: any): void {
    this.selectFilter = event.target.value;
    this.doFilter();
  }

  private doFilter(): void {
    const filterFns: ((user: User) => boolean)[] = [];

    if (this.selectFilter) {
      switch (this.selectFilter) {
        case 'disabled':
          filterFns.push(user => !user.enabled);
          break;
        case 'enabled':
          filterFns.push(user => user.enabled);
          break;
        case 'inactive':
          filterFns.push(user => user.expired);
          break;
        case 'admin':
          filterFns.push(user => user.admin);
          break;
        default:
          this.users$ = this.allUsers$;
      }
    }

    if (this.searchFilter) {
      filterFns.push(user => user.email.includes(this.searchFilter!));
    }

    this.filter(user => filterFns.every(fn => fn(user)));
  }

  private filter(filterFn: (user: User) => boolean): void {
    this.users$ = this.allUsers$.pipe(
      mergeMap(users => users),
      filter(filterFn),
      toArray());
  }
}
