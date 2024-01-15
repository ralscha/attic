import {Recipe} from "../models/recipe";
import {Ingredient} from "../models/ingredient";
import {Http, Response} from "@angular/http";
import {AuthService} from "./auth-service";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import {Injectable} from "@angular/core";

@Injectable()
export class RecipeService {
  private recipes: Recipe[] = [];

  constructor(private http: Http, private authService: AuthService) {
  }

  addRecipe(title: string, description: string,
            difficulty: 'Easy'|'Medium'|'Hard',
            ingredients: Ingredient[]) {
    this.recipes.push(new Recipe(title, description, difficulty, ingredients));
  }

  getRecipes() {
    return this.recipes.slice();
  }

  updateRecipe(index: number, title: string, description: string,
               difficulty: 'Easy'|'Medium'|'Hard',
               ingredients: Ingredient[]) {
    this.recipes[index] = new Recipe(title, description, difficulty, ingredients);
  }

  removeRecipe(index: number) {
    this.recipes.splice(index, 1);
  }

  storeList(token: string) {
    const userId = this.authService.getActiveUser().uid;
    return this.http.put(`https://ionic2-recipebook-4c30e.firebaseio.com/${userId}/recipes.json?auth=${token}`,
      this.recipes).map((response: Response) => response.json());
  }

  fetchList(token: string) {
    const userId = this.authService.getActiveUser().uid;
    return this.http.get(`https://ionic2-recipebook-4c30e.firebaseio.com/${userId}/recipes.json?auth=${token}`)
      .map(response => {
        const recipes:Recipe[] = response.json() ? response.json() : [];
        for (let item of recipes) {
          if (!item.hasOwnProperty('ingredients')) {
            item.ingredients = [];
          }
        }
        return recipes;
      }).do(data => {
        if (data) {
          this.recipes = data;
        }
        else {
          this.recipes = [];
        }
      });
  }

}
