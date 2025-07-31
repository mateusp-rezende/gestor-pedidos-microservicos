import React, { useState, useEffect, useCallback } from 'react';
import { PlusIcon, LoadingSpinner } from '../components/icons';

const VendedoresPage = () => {
  const [vendedores, setVendedores] = useState([]);
  const [empresas, setEmpresas] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const [novoVendedor, setNovoVendedor] = useState({ nome: '', empresaId: '' });

  const API_URL = 'http://localhost:8700';
  const USERNAME = 'mateus';
  const PASSWORD = 'senha123';

  const fetchData = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

    try {
      const [vendedoresRes, empresasRes] = await Promise.all([
        fetch(`${API_URL}/vendedores/`, { headers }),
        fetch(`${API_URL}/empresas/`, { headers }),
      ]);

      if (!vendedoresRes.ok || !empresasRes.ok) throw new Error('Falha ao buscar dados da API.');

      const vendedoresData = await vendedoresRes.json();
      const empresasData = await empresasRes.json();

      setVendedores(vendedoresData);
      setEmpresas(empresasData);

      // Se só existir uma empresa, pré-seleciona ela automaticamente
      if (empresasData.length === 1) {
        setNovoVendedor(prev => ({ ...prev, empresaId: empresasData[0].id }));
      } else {
        setNovoVendedor(prev => ({ ...prev, empresaId: '' }));
      }

    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNovoVendedor(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!novoVendedor.empresaId) {
      setError("Por favor, selecione uma empresa.");
      return;
    }
    setIsSubmitting(true);
    setError(null);

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));
    headers.append('Content-Type', 'application/json');

    const payload = {
      nome: novoVendedor.nome,
      empresa: {
        id: novoVendedor.empresaId
      }
    };

    try {
      const response = await fetch(`${API_URL}/vendedores/`, {
        method: 'POST',
        headers,
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error('Falha ao cadastrar o vendedor.');

      setNovoVendedor({ nome: '', empresaId: empresas.length === 1 ? empresas[0].id : '' });
      setShowForm(false);
      fetchData();
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="space-y-8">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-800">Equipa de Vendas</h1>
          <p className="text-gray-500 mt-1">Gerencie os vendedores do sistema.</p>
        </div>
        <button
          onClick={() => setShowForm(!showForm)}
          className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg flex items-center"
        >
          <PlusIcon /> <span className="ml-2">{showForm ? 'Cancelar' : 'Novo Vendedor'}</span>
        </button>
      </div>

      {error && (
        <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded-md">
          <p>{error}</p>
        </div>
      )}

      {showForm && (
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">Novo Vendedor</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              name="nome"
              value={novoVendedor.nome}
              onChange={handleInputChange}
              placeholder="Nome do Vendedor"
              className="w-full p-2 border rounded-md bg-gray-50"
              required
            />

            {/* Se existir apenas uma empresa, mostra o nome fixo */}
            {empresas.length === 1 ? (
              <p className="mb-4 text-gray-700">
                Empresa: <strong>{empresas[0].nome}</strong>
              </p>
            ) : (
              // Caso contrário, mostra o select para escolher empresa
              <select
                name="empresaId"
                value={novoVendedor.empresaId}
                onChange={handleInputChange}
                className="w-full p-2 border rounded-md bg-gray-50"
                required
              >
                <option value="" disabled>Selecione uma empresa</option>
                {empresas.map(empresa => (
                  <option key={empresa.id} value={empresa.id}>{empresa.nome}</option>
                ))}
              </select>
            )}

            {empresas.length === 0 && (
              <p className="text-sm text-yellow-600 p-2 bg-yellow-50 rounded-md">
                É necessário cadastrar uma empresa antes de adicionar um vendedor.
              </p>
            )}

            <button
              type="submit"
              disabled={isSubmitting || empresas.length === 0}
              className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-lg flex justify-center items-center disabled:bg-gray-400"
            >
              {isSubmitting ? <LoadingSpinner /> : 'Salvar Vendedor'}
            </button>
          </form>
        </div>
      )}

      <div className="bg-white p-6 rounded-lg shadow-md overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead className="bg-gray-100">
            <tr>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Nome</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Empresa</th>
            </tr>
          </thead>
          <tbody className="text-gray-700">
            {isLoading ? (
              <tr><td colSpan="2" className="text-center py-4">A carregar vendedores...</td></tr>
            ) : vendedores.length > 0 ? (
              vendedores.map(vendedor => (
                <tr key={vendedor.id} className="border-b border-gray-200 hover:bg-gray-50">
                  <td className="py-3 px-4 font-medium">{vendedor.nome}</td>
                  <td className="py-3 px-4 text-sm">{vendedor.empresa ? vendedor.empresa.nome : 'N/A'}</td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="2" className="text-center py-4">Nenhum vendedor cadastrado.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default VendedoresPage;
