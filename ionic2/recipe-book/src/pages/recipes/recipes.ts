import {Component} from '@angular/core';
import {NavController, PopoverController, LoadingController, AlertController} from "ionic-angular";
import {EditRecipePage} from "../edit-recipe/edit-recipe";
import {Recipe} from "../../models/recipe";
import {RecipeService} from "../../providers/recipe-service";
import {RecipePage} from "../recipe/recipe";
import {AuthService} from "../../providers/auth-service";
import {ShoppingListOptionsPage} from "../shopping-list-options/shopping-list-options";

@Component({
  selector: 'page-recipes',
  templateUrl: 'recipes.html'
})
export class RecipesPage {

  recipes: Recipe[];

  constructor(private readonly navController: NavController,
              private readonly recipeService: RecipeService,
              private popoverCtrl: PopoverController,
              private authService: AuthService,
              private loadingCtrl: LoadingController,
              private alertCtrl: AlertController) {
  }

  ionViewWillEnter() {
    this.recipes = this.recipeService.getRecipes();
  }

  onLoadRecipe(recipe: Recipe, index: number) {
    this.navController.push(RecipePage, {recipe, index});
  }

  onNewRecipe() {
    this.navController.push(EditRecipePage, {'mode': 'New'});
  }

  onShowOptions(event: MouseEvent) {
    const loading = this.loadingCtrl.create({
      content: 'Please wait...'
    });

    const popover = this.popoverCtrl.create(ShoppingListOptionsPage);
    popover.present({ev: event});
    popover.onDidDismiss(data => {
      if (!data) {
        return;
      }
      if (data.action === 'load') {
        loading.present();
        this.authService.getActiveUser().getToken()
          .then(token => {
            this.recipeService.fetchList(token).subscribe(list => {
                if (list) {
                  this.recipes = list;
                }
                else {
                  this.recipes = [];
                }
                loading.dismiss();
              },
              error => {loading.dismiss(); this.handleError(error.json().error);}
            );
          });
      }
      else if (data.action === 'save') {
        loading.present();
        this.authService.getActiveUser().getToken()
          .then(token => {
            this.recipeService.storeList(token).subscribe(() => loading.dismiss(),
              error => {loading.dismiss(); this.handleError(error.json().error);}
            );
          });
      }
    });
  }

  private handleError(errorMessage: string) {
    const alert = this.alertCtrl.create({
      title: 'An error occurred!',
      message: errorMessage,
      buttons: ['OK']
    });
    alert.present();
  }
}
