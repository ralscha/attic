export interface Movie {
  movieName: string;
  rating: number;
  genre: string;
}

export class Storage {

  setItem(itemName: string, movies: Movie[]): void {
    localStorage.setItem(itemName, JSON.stringify(movies));
  }

  getItems(itemKey: string): Movie[] {
    const data: string = localStorage.getItem(itemKey);
    if (data) {
      return JSON.parse(data);
    }
    return [];
  }

  removeItem(itemKey: string) {
    localStorage.removeItem(itemKey);
  }

}
