/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      // Aqui podemos adicionar personalizações para o nosso tema.
      // Isto ajuda a manter o design consistente em toda a aplicação.
      colors: {
        'brand-primary': '#3B82F6', // Azul principal da nossa "marca" (ex: botões)
        'brand-secondary': '#10B981', // Verde para sucesso e confirmação
        'brand-background': '#F9FAFB', // Um cinza muito claro para o fundo das páginas
      },
      fontFamily: {
        // Define uma fonte personalizada para o projeto.
        // Lembre-se de importar esta fonte no seu index.html ou CSS principal.
        sans: ['Inter', 'sans-serif'],
      },
      // Podemos até personalizar os breakpoints (pontos de quebra para diferentes tamanhos de ecrã) se precisarmos.
      // Os padrões do Tailwind (sm, md, lg, xl) já são excelentes para começar.
      // screens: {
      //   'tablet': '640px',
      //   'laptop': '1024px',
      //   'desktop': '1280px',
      // },
    },
  },
  plugins: [],
}