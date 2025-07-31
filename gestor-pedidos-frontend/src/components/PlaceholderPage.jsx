import React from 'react';

// Componente genérico para as páginas que ainda vamos construir
const PlaceholderPage = ({ title }) => (
    <div className="text-center p-10 bg-white rounded-lg shadow-md">
        <h1 className="text-3xl font-bold text-gray-800 mb-4">{title}</h1>
        <p className="text-gray-500">Esta funcionalidade será implementada em breve.</p>
        <p className="text-gray-400 mt-2 text-sm">// TODO: Ligar esta página à API e construir a UI.</p>
    </div>
);

export default PlaceholderPage;