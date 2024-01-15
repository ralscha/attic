import {Component, OnInit} from '@angular/core';
import {NavParams, ActionSheetController, AlertController, ToastController, NavController} from "ionic-angular";
import {FormGroup, FormControl, Validators, FormArray} from "@angular/forms";
import {RecipeService} from "../../providers/recipe-service";
import {Recipe} from "../../models/recipe";

@Component({
  selector: 'page-edit-recipe',
  templateUrl: 'edit-recipe.html'
})
export class EditRecipePage implements OnInit {
  mode = 'New';
  selectOptions = ['Easy', 'Medium', 'Hard'];
  recipeForm: FormGroup;
  recipe: Recipe;
  index: number;

  constructor(private readonly navParams: NavParams,
              private readonly actionSheetController: ActionSheetController,
              private readonly alertController: AlertController,
              private readonly toastController: ToastController,
              private readonly recipeService: RecipeService,
              private readonly navController: NavController) {
  }

  ngOnInit() {
    this.mode = this.navParams.get('mode');
    if (this.mode === 'Edit') {
      this.recipe = this.navParams.get('recipe');
      this.index = this.navParams.get('index');
    }
    this.initializeForm();
  }

  onSubmit() {
    const value = this.recipeForm.value;
    let ingredients = [];
    if (value.ingredients.length > 0) {
      ingredients = value.ingredients.map(name => { return {name, amount: 1}; });
    }
    if (this.mode === 'Edit') {
      this.recipeService.updateRecipe(this.index, value.title, value.description, value.difficulty, ingredients);
    }
    else {
      this.recipeService.addRecipe(value.title, value.description, value.difficulty, ingredients);
    }
    this.recipeForm.reset();
    this.navController.popToRoot();
  }

  onManageIngredients() {
    const actionSheet = this.actionSheetController.create({
      title: 'What do you want to do?',
      buttons: [{
        text: 'Add Ingredient',
        handler: () => this.createNewIngredientAlert().present()
      }, {
        text: 'Remove all Ingredients',
        role: 'destructive',
        handler: () => {
          const fa = <FormArray>this.recipeForm.get('ingredients');
          const len = fa.length;
          if (len > 0) {
            for (let i = len - 1; i >= 0; i--) {
              fa.removeAt(i);
            }
            this.toastController.create({
              message: 'All Ingredients were deleted',
              duration: 2000,
              position: 'bottom'
            }).present();
          }
        }
      }, {
        text: 'Cancel',
        role: 'cancel'
      }]
    });
    actionSheet.present();
  }

  private createNewIngredientAlert() {
    return this.alertController.create({
      title: 'Add Ingredient',
      inputs: [{
        name: 'name',
        placeholder: 'Name'
      }],
      buttons: [{
        text: 'Cancel',
        role: 'cancel'
      }, {
        text: 'Add',
        handler: data => {
          if (data.name == null || data.name.trim() === '') {
            this.toastController.create({
              message: 'Please enter a valid name',
              duration: 3000,
              position: 'bottom'
            }).present();
            return;
          }
          (<FormArray>this.recipeForm.get('ingredients')).push(new FormControl(data.name, Validators.required));
          this.toastController.create({
            message: 'Item added',
            duration: 2000,
            position: 'bottom'
          }).present();
        }
      }]
    });
  }

  private initializeForm() {
    let title = null;
    let description = null;
    let difficulty = 'Medium';
    let ingredients = [];

    if (this.mode === 'Edit') {
      title = this.recipe.title;
      description = this.recipe.description;
      difficulty = this.recipe.difficulty;
      for (let ing of this.recipe.ingredients) {
        ingredients.push(new FormControl(ing.name, Validators.required));
      }
    }

    this.recipeForm = new FormGroup({
      title: new FormControl(title, Validators.required),
      description: new FormControl(description, Validators.required),
      difficulty: new FormControl(difficulty, Validators.required),
      ingredients: new FormArray(ingredients)
    });
  }
}
