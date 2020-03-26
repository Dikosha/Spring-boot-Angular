import { Component, OnInit } from '@angular/core';
import {ScriptLoadingService} from '../_services/script-loading.service';


@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  constructor(private scriptLoadingService: ScriptLoadingService) { }

  ngOnInit(): void {
    this.scriptLoadingService.loadScript();
  }

}
