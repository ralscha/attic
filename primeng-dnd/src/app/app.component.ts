import {Component, OnInit, signal} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {TableModule} from "primeng/table";
import {Product} from "./product";
import {ProductService} from "./product.service";
import {DragDropModule} from "primeng/dragdrop";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [
    ButtonModule,
    TableModule,
    DragDropModule
  ],
  styleUrl: './app.component.css',
  providers: [ProductService]
})
export class AppComponent implements OnInit {
  draggedProduct: Product | null = null;
  draggedSelectedProduct: Product | null = null;
  selectedProducts: Product[] = [];
  availableProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.selectedProducts = [];
    this.productService.getProductsMini().then(data => this.availableProducts = data);
  }

  dragStart(product: Product) {
    this.draggedProduct = product;
  }

  drop() {
    if (this.draggedProduct) {
      this.selectedProducts = [...this.selectedProducts, this.draggedProduct];
      this.availableProducts = this.availableProducts?.filter(p => p.id !== this.draggedProduct?.id);
      this.draggedProduct = null;
    }
  }

  dragEnd() {
    this.draggedProduct = null;
  }

  dragStartSelectedProducts(product: Product) {
    console.log("dragStartSelectedProducts");
    this.draggedSelectedProduct = product;
  }

  dropSelectedProducts() {
    console.log("dropSelectedProducts");
    if (this.draggedSelectedProduct) {
      this.availableProducts = [...this.availableProducts, this.draggedSelectedProduct];
      this.selectedProducts = this.selectedProducts?.filter(p => p.id !== this.draggedSelectedProduct?.id);
      this.draggedSelectedProduct = null;
    }
  }

  dragEndSelectedProducts() {
    this.draggedSelectedProduct = null;
  }
}
