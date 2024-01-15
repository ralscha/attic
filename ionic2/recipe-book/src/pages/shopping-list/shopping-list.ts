import {Component} from "@angular/core";
import {NgForm} from "@angular/forms";
import {ShoppingListService} from "../../providers/shopping-list-service";
import {Ingredient} from "../../models/ingredient";
import {PopoverController, LoadingController, AlertController} from "ionic-angular";
import {ShoppingListOptionsPage} from "../shopping-list-options/shopping-list-options";
import {AuthService} from "../../providers/auth-service";

@Component({
  selector: 'page-shopping-list',
  templateUrl: 'shopping-list.html'
})
export class ShoppingListPage {

  items: Ingredient[];

  constructor(private shoppingListService: ShoppingListService,
              private popoverCtrl: PopoverController,
              private authService: AuthService,
              private loadingCtrl: LoadingController,
              private alertCtrl: AlertController) {
  }

  onAddItem(f: NgForm) {
    this.shoppingListService.addItem(f.value.ingredientName, f.value.amount);
    f.reset();
    this.loadItems();
  }

  ionViewWillEnter() {
    this.loadItems();
  }

  onRemoveItem(ix: number) {
    this.shoppingListService.removeItem(ix);
    this.loadItems();
  }

  private loadItems() {
    this.items = this.shoppingListService.getItems();
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
            this.shoppingListService.fetchList(token).subscribe(list => {
                if (list) {
                  this.items = list;
                }
                else {
                  this.items = [];
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
            this.shoppingListService.storeList(token).subscribe(() => loading.dismiss(),
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
