import React from 'react';
import { MenuIcon } from './icons';

// Este componente é o cabeçalho que só aparece em ecrãs pequenos
const Header = ({ onMenuClick }) => {
    return (
        // A classe 'md:hidden' faz este header desaparecer em ecrãs médios e grandes
        <header className="md:hidden bg-white shadow-md p-4 flex items-center">
            <button onClick={onMenuClick} className="text-gray-600 hover:text-gray-800">
                <MenuIcon />
            </button>
            <h2 className="text-xl font-bold text-blue-600 ml-4">Alfred</h2>
        </header>
    );
};

export default Header;