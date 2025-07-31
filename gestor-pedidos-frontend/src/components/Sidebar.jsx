import React from 'react';
import { HomeIcon, ClipboardIcon, PackageIcon, UsersIcon, UserCheckIcon, LogOutIcon, XIcon } from './icons';
import Logo from '../assets/logo-principal.png'; 

const Sidebar = ({ currentPage, onNavigate, onLogout, isOpen, setIsOpen }) => {
    const navItems = [
        { id: 'dashboard', label: 'Dashboard', icon: <HomeIcon /> },
        { id: 'pedidos', label: 'Pedidos', icon: <ClipboardIcon /> },
        { id: 'produtos', label: 'Produtos', icon: <PackageIcon /> },
        { id: 'clientes', label: 'Clientes', icon: <UsersIcon /> },
        { id: 'vendedores', label: 'Vendedores', icon: <UserCheckIcon /> },
    ];
    
    const handleNavigate = (page) => {
        onNavigate(page);
        setIsOpen(false); // Fecha a sidebar após a navegação em ecrãs pequenos
    }

    return (
        <>
            {/* Overlay para o fundo escuro em ecrãs pequenos */}
            <div 
                className={`fixed inset-0 bg-black bg-opacity-50 z-30 md:hidden transition-opacity ${isOpen ? 'opacity-100' : 'opacity-0 pointer-events-none'}`}
                onClick={() => setIsOpen(false)}
            ></div>

            {/* Sidebar */}
            <aside className={`fixed md:relative inset-y-0 left-0 w-64 bg-white shadow-lg flex flex-col transform ${isOpen ? 'translate-x-0' : '-translate-x-full'} md:translate-x-0 transition-transform duration-300 ease-in-out z-40`}>
                <div className="h-16 flex items-center justify-between border-b px-4">
                    <img className={`w-20 h-20`}src={Logo}/>
                    <h2 className="text-2xl font-bold text-blue-600">Alfred</h2>
                    <button onClick={() => setIsOpen(false)} className="md:hidden text-gray-600 hover:text-gray-800">
                        <XIcon />
                    </button>
                </div>
                <nav className="flex-1 px-4 py-4 space-y-2">
                    {navItems.map(item => (
                        <button 
                            key={item.id} 
                            onClick={() => handleNavigate(item.id)} 
                            className={`w-full flex items-center px-4 py-2 text-gray-700 rounded-lg transition-colors duration-200 ${currentPage === item.id ? 'bg-blue-100 text-blue-600' : 'hover:bg-gray-200'}`}
                        >
                            {item.icon}
                            <span className="mx-4 font-medium">{item.label}</span>
                        </button>
                    ))}
                </nav>
                <div className="px-4 py-4 border-t">
                    <button onClick={onLogout} className="w-full flex items-center px-4 py-2 text-gray-700 rounded-lg hover:bg-gray-200">
                        <LogOutIcon />
                        <span className="mx-4 font-medium">Sair</span>
                    </button>
                </div>
            </aside>
        </>
    );
};

export default Sidebar;