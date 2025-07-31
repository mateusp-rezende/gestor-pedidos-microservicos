import React from 'react';
import Logo from '../assets/logo-principal.png'; 
import ImagemDeFundo from '../assets/tela-login2.jpg'; 

const LoginPage = ({ onLogin }) => (
  <div className="flex min-h-screen">
    {/* LADO ESQUERDO - LOGIN */}
    <div className="w-full md:w-1/2 flex items-center justify-center bg-white p-8 shadow-lg z-10">
      <div className="w-full max-w-md space-y-8">
        <div className="text-center">
          <img className={`w-20 h-20 mx-auto mb-1 `}src={Logo}/>
           <p className="mt-2 text-gray-400">Gestor de Pedidos Alfred</p>
          <h2 className="text-3xl font-bold text-gray-900">Seja bem-vindo!</h2>
         
        </div>
        <form
          className="space-y-6"
          onSubmit={(e) => {
            e.preventDefault();
            onLogin();
          }}
        >
          <input
            className="w-full px-4 py-2 text-gray-700 bg-gray-100 border rounded-md focus:border-blue-500 focus:ring-blue-500"
            type="text"
            placeholder="Utilizador"
            required
            defaultValue="mateus"
          />
          <input
            className="w-full px-4 py-2 text-gray-700 bg-gray-100 border rounded-md focus:border-blue-500 focus:ring-blue-500"
            type="password"
            placeholder="Senha"
            required
            defaultValue="senha123"
          />
          <button
            type="submit"
            className="w-full px-4 py-2 font-bold text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
          >
            Entrar
          </button>
        </form>
      </div>
    </div>

    {/* LADO DIREITO - INFORMAÇÃO VISUAL */}
    <div className="hidden md:flex w-1/2 items-center justify-center bg-blue-600 text-white relative overflow-hidden">
      <div className="p-12 text-center z-10">
       <h2 className="text-4xl font-bold mb-4 drop-shadow-md text-white">
  Gerencie suas vendas!
</h2>
<p className="text-lg text-blue-100 drop-shadow-sm">
  O Alfred é um sistema intuitivo de controle de pedidos, pensado para otimizar seu tempo e organizar suas vendas com eficiência.
</p>

      </div>
      <div className="absolute inset-0 opacity-20 z-0">
         <img className={`w-full h-full object-cover`}src={ImagemDeFundo}/>
        
      </div>
    </div>
  </div>
);

export default LoginPage;
