import { Routes } from '@angular/router';
import {ChatComponent} from './components/chat/chat.component';
import {CryptosComponent} from './components/cryptos/cryptos.component';

export const routes: Routes = [
  {path: "ai-chatbot", component: ChatComponent},
  {path: "cryptos", component: CryptosComponent}
];
