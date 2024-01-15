import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";
import {SERVER_URL} from "./config";
import {Player} from "./player";
import {Track} from "./track";

@Injectable()
export class PlayerService {

  private players: Player[] = [];
  private lastPos: number = 0;

  constructor(private readonly http: Http) {
  }

  fetchPlayers() {
    this.http.get(`${SERVER_URL}/spieler`)
      .map(response => response.json())
      .subscribe(data => {
        for (let r of data[0]) {
          const {id, nachname, vorname, lizenzNr, jahrgang, position} = r;
          if (position > this.lastPos) {
            this.lastPos = position;
          }
          this.players.push(new Player(id, nachname, vorname, lizenzNr, jahrgang, position, false));
        }
        for (let r of data[1]) {
          const {id, nachname, vorname, lizenzNr, jahrgang, position} = r;
          if (position > this.lastPos) {
            this.lastPos = position;
          }
          this.players.push(new Player(id, nachname, vorname, lizenzNr, jahrgang, position, true));
        }
      });
  }

  getRegularPlayers(): Player[] {
    return this.players.filter(p => !p.ueberzaehlig);
  }

  getUeberzaehligePlayers(): Player[] {
    return this.players.filter(p => p.ueberzaehlig);
  }

  hasNext(track: Track): boolean {
    if (!track) {
      return false;
    }
    return !(track.player.position === this.lastPos && track.ries === 3);
  }

  hasPrev(track: Track): boolean {
    if (!track) {
      return false;
    }
    return !(track.player.position === 1 && track.ries === 0);
  }

  next(track: Track): Track {
    if (this.hasNext(track)) {
      if (track.ries === 0 || track.ries === 2) {
        return {player: track.player, ries: track.ries + 1};
      }
      if (track.ries === 1 || track.ries === 3) {
        let nextPos;
        if (track.player.position === this.lastPos) {
          nextPos = 1;
          return {player: this.players.find(p => p.position === nextPos), ries: 2};
        }
        else {
          nextPos = track.player.position + 1;
          return {player: this.players.find(p => p.position === nextPos), ries: track.ries - 1};
        }
      }
    }

    return null;
  }

  back(track: Track): Track {
    if (this.hasPrev(track)) {
      if (track.ries === 1 || track.ries === 3) {
        return {player: track.player, ries: track.ries - 1};
      }
      if (track.ries === 0 || track.ries === 2) {
        let prevPos;
        if (track.player.position === 1) {
          prevPos = this.lastPos;
          return {player: this.players.find(p => p.position === prevPos), ries: 1};
        }
        else {
          prevPos = track.player.position - 1;
          return {player: this.players.find(p => p.position === prevPos), ries: track.ries + 1};
        }
      }
    }

    return null;
  }

  clear() {
    for (let p of this.players) {
      p.no = [false, false, false, false];
      p.ries = [];
    }
    this.http.get(`${SERVER_URL}/clear`).subscribe(() => {
    });
  }

  postPunkte(track: Track) {
    this.http.post(`${SERVER_URL}/postPunkte`, {
      spieler: track.player.id,
      ries: track.ries,
      punkt: track.player.ries[track.ries],
      nr: track.player.no[track.ries]
    }).subscribe(() => {
    });
  }

}
