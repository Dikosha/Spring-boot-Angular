import { Injectable } from '@angular/core';
import {variable} from '@angular/compiler/src/output/output_ast';


@Injectable({
    providedIn: 'root'
})
export class ScriptLoadingService {
    private url = [
        'assets/js/jquery-3.3.1.min.js',
        'assets/js/jquery-migrate-3.0.0.js',
        'assets/js/popper.min.js',
        'assets/js/bootstrap.min.js',
        'assets/js/owl.carousel.min.js',
        'assets/js/jquery.sticky.js',
        'assets/js/jquery.waypoints.min.js',
        'assets/js/jquery.animateNumber.min.js',
        'assets/js/jquery.fancybox.min.js',
        'assets/js/jquery.stellar.min.js',
        'assets/js/jquery.easing.1.3.js',
        'assets/js/bootstrap-datepicker.min.js',
        'assets/js/aos.js',
        'assets/js/main.js',
    ];
    private i = 0;

    constructor() { }

    public loadScript() {
        const n = this.deleteScript();
        // console.log(n);
        // console.log('12345');
        for (this.i = 0; this.i < this.url.length; this.i++) {
            // if (n === 0) {
            //     let node = document.createElement('script');
            //     node.src = this.url[this.i];
            //     node.type = 'text/javascript';
            //     // node.async = true;
            //     node.charset = 'utf-8';
            //     document.getElementsByTagName('body')[0].appendChild(node);
            // }
            const node = document.createElement('script');
            node.src = this.url[this.i];
            node.setAttribute('class', 'scripts');
            node.type = 'text/javascript';
            // node.async = true;
            node.charset = 'utf-8';
            document.getElementsByTagName('head')[0].appendChild(node);
        }
    }
    private deleteScript() {
        const n = document.getElementsByClassName('scripts');
        const len = n.length;
        for (this.i = 0; this.i < len; this.i++) {
            // document.getElementsByClassName('scripts')[this.i].remove();
            n[0].remove();
        }
        if (len === 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
