import {Component, OnInit} from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {Recipe} from "../../models/recipe";
import {EditRecipePage} from "../edit-recipe/edit-recipe";
import {ShoppingListService} from "../../providers/shopping-list-service";
import {RecipeService} from "../../providers/recipe-service";

@Component({
  selector: 'page-recipe',
  templateUrl: 'recipe.html'
})
export class RecipePage implements OnInit {
  recipe: Recipe;
  index: number;

  constructor(private readonly navCtrl: NavController,
              private readonly navParams: NavParams,
              private readonly shoppingListServie: ShoppingListService,
              private readonly recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipe = this.navParams.get('recipe');
    this.index = this.navParams.get('index');
  }

  onAddIngredients() {
    this.shoppingListServie.addItems(this.recipe.ingredients);
  }

  onEditRecipe() {
    this.navCtrl.push(EditRecipePage, {mode: 'Edit', recipe: this.recipe, index: this.index});
  }

  onDeleteRecipe() {
    this.recipeService.removeRecipe(this.index);
    this.navCtrl.popToRoot();
  }

}
