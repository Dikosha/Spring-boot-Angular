import { Component, OnInit } from '@angular/core';
import {ScriptLoadingService} from '../_services/script-loading.service';

@Component({
  selector: 'app-barber-shop',
  templateUrl: './barber-shop.component.html',
  styleUrls: ['./barber-shop.component.css']
})
export class BarberShopComponent implements OnInit {

  constructor(private scriptLoadingService: ScriptLoadingService) { }

  ngOnInit(): void {
    this.scriptLoadingService.loadScript();
  }

}
