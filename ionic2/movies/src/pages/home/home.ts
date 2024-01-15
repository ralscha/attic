import {Component, trigger, state, style, transition, animate, ViewChild} from '@angular/core';
import {Content, ToastController, Platform, AlertController} from 'ionic-angular';
import {FormGroup, Validators, FormBuilder} from '@angular/forms';
import {Storage, Movie} from '../../providers/storage';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
  animations: [
    trigger('panelHeightTrigger', [
      state('expanded', style({height: '370px', visibility: 'visible'})),
      state('collapsed', style({height: '0px', visibility: 'hidden'})),
      transition('collapsed => expanded', animate('1000ms ease-in')),
      transition('expanded => collapsed', animate('1000ms 200ms ease-out'))
    ]),

    trigger('filtersPanelHeightTrigger', [
      state('expandedState', style({height: '300px', visibility: 'visible'})),
      state('collapsedState', style({height: '0px', visibility: 'hidden'})),
      transition('collapsedState => expandedState', animate('1000ms ease-in')),
      transition('expandedState => collapsedState', animate('1000ms 200ms ease-out'))
    ])
  ]
})
export class HomePage {
  @ViewChild(Content) content: Content;

  public form: FormGroup;
  public storageKey: string = "MoviesObj";
  public movies: Movie[] = [];
  public moviesList: Movie[] = [];
  public isEdited: boolean = false;

  // Edits
  public expanded: boolean = false;
  public expandedState: string = 'collapsed';
  public expandedFilters: boolean = false;
  public expandedFiltersState: string = 'collapsedState';
  public buttonText: string = "Add a new movie";
  public filtersText: string = "Display Filters";

  constructor(public alertCtrl: AlertController,
              public storage: Storage,
              public fb: FormBuilder,
              public toastCtrl: ToastController,
              public platform: Platform) {
    this.renderMovies();
    this.form = fb.group({
      "movieName": ["", Validators.required],
      "genre": ["", Validators.required],
      "rating": ["", Validators.required]
    });
  }

  renderMovies(filterType: 'rating'|'genre' = null, filterValue: (string|number) = null): void {
    this.movies = this.storage.getItems(this.storageKey);
    this.moviesList = [];
    if (filterType != null && this.movies.length > 0) {
      for (let movie of this.movies) {
        if (filterType === 'rating') {
          if (movie.rating === filterValue) {
            this.moviesList.push(movie);
          }
        }
        else {
          if (movie.genre === filterValue) {
            this.moviesList.push(movie);
          }
        }
      }
    }
    else {
      this.moviesList = this.movies;
    }

    if (this.moviesList.length === 0) {
      const message = "You don't have any movies to display. Why not add some?";
      this.displayAlert(message);
    }
  }

  filterMoviesByGenre(genreFilter: string) {
    console.log(genreFilter);
    if (genreFilter !== 'All') {
      this.renderMovies('genre', genreFilter);
    }
    else {
      this.renderMovies();
    }
    this.toggleFilters();
  }

  filterMoviesByRating(ratingFilter: any) {
    this.renderMovies('rating', ratingFilter.value);
    this.toggleFilters();
  }


  displayAlert(message: string): void {
    const headsUp = this.alertCtrl.create({
      title: 'Heads Up!',
      subTitle: message,
      buttons: ['Got It!']
    });
    headsUp.present();
  }

  saveMovie() {
    let movieName: string = this.form.controls["movieName"].value;
    let exists: boolean = false;

    for (let m of this.movies) {
      if (m.movieName === movieName) {
        exists = true;
        break;
      }
    }

    if (!exists) {
      this.movies.push({
        movieName: movieName,
        rating: this.form.controls['rating'].value,
        genre: this.form.controls['genre'].value
      });
      this.sortMoviesByTitle();
      if (this.isEdited) {
        this.storeMovie(movieName, 'updated');
      }
      else {
        this.storeMovie(movieName, 'added');
      }
      this.isEdited = false;
      this.toggleForm();
    }
    else {
      const message = `The movie ${movieName} has already been stored. Please enter a different movie title.`;
      this.storageNotification(message);
    }
  }

  scrollToTopOfScreen() {
    this.content.scrollToTop();
  }

  editMovie(movie: Movie): void {
    this.scrollToTopOfScreen();
    this.form.controls['movieName'].reset(movie.movieName);
    this.form.controls['rating'].reset(movie.rating);
    this.form.controls['genre'].reset(movie.genre);
    this.isEdited = true;

    const ix = this.movies.indexOf(movie);
    if (ix >= 0) {
      this.movies.splice(ix, 1);
      this.storage.setItem(this.storageKey, this.movies);
    }
    this.toggleForm();
  }

  deleteMovie(movie: Movie): void {
    const ix = this.movies.indexOf(movie);
    if (ix >= 0) {
      this.movies.splice(ix, 1);
      this.storeMovie(movie.movieName, 'removed');
    }
  }

  storeMovie(movieName: string, action: string) {
    this.storage.setItem(this.storageKey, this.movies);
    this.clearFieldValues();
    this.storageNotification(`The move title ${movieName} was successfully ${action}!`);
  }

  clearFieldValues() {
    this.form.controls['movieName'].reset('');
    this.form.controls['rating'].reset('');
    this.form.controls['genre'].reset('');
  }

  storageNotification(message: string): void {
    const notification = this.toastCtrl.create({
      message: message,
      duration: 3000
    });
    notification.present();
  }

  sortMoviesByTitle() {
    this.movies.sort((a, b) => {
        const x = a.movieName.toLowerCase();
        const y = b.movieName.toLowerCase();
        return x < y ? -1 : x > y ? 1 : 0;
      }
    );
  }

  toggleForm() {
    this.expanded = !this.expanded;

    if (this.expanded) {
      this.buttonText = "Nah, just display my movies";
      this.expandedState = 'expanded';
    }
    else {
      this.buttonText = "Add a new movie";
      this.expandedState = 'collapsed';
    }
  }

  toggleFilters() {
    this.expandedFilters = !this.expandedFilters;

    if (this.expandedFilters) {
      this.filtersText = "Hide these filters";
      this.expandedFiltersState = 'expandedState';
    }
    else {
      this.filtersText = "Display Filters";
      this.expandedFiltersState = 'collapsedState';
    }
  }
}
