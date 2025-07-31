import React, { useState } from 'react';
import Sidebar from './components/Sidebar';
import Header from './components/Header'; // Importa o novo Header
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import PedidosPage from './pages/PedidosPage';
import ProdutosPage from './pages/ProdutosPage';
import ClientesPage from './pages/ClientesPage';
import VendedoresPage from './pages/VendedoresPage';

export default function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [currentPage, setCurrentPage] = useState('dashboard');
    // Novo estado para controlar a visibilidade da sidebar em ecrãs pequenos
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const renderPage = () => {
        switch (currentPage) {
            case 'dashboard': return <DashboardPage />;
            case 'pedidos': return <PedidosPage />;
            case 'produtos': return <ProdutosPage />;
            case 'clientes': return <ClientesPage />;
            case 'vendedores': return <VendedoresPage />;
            default: return <DashboardPage />;
        }
    };

    if (!isLoggedIn) {
        return <LoginPage onLogin={() => setIsLoggedIn(true)} />;
    }

    return (
        <div className="flex h-screen bg-gray-100 font-sans">
            <Sidebar 
                currentPage={currentPage} 
                onNavigate={setCurrentPage} 
                onLogout={() => setIsLoggedIn(false)}
                isOpen={isSidebarOpen}
                setIsOpen={setIsSidebarOpen}
            />
            <div className="flex-1 flex flex-col overflow-hidden">
                <Header 
                    onMenuClick={() => setIsSidebarOpen(true)} 
                />
                <main className="flex-1 p-6 lg:p-10 overflow-y-auto">
                    {renderPage()}
                </main>
            </div>
        </div>
    );
}