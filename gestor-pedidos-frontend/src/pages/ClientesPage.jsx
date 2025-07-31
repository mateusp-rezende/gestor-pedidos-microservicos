import React, { useState, useEffect, useCallback } from 'react';
import { PlusIcon, LoadingSpinner,PencilIcon,TrashIcon } from '../components/icons';

const ClientesPage = () => {
  const [clientes, setClientes] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [modoEdicao, setModoEdicao] = useState(false);
  const [clienteEditandoId, setClienteEditandoId] = useState(null);

  const [novoCliente, setNovoCliente] = useState({
    nome: '',
    telefone: '',
    email: '',
    endereco: '',
    cpfOuCnpj: '',
  });

  const API_URL = 'http://localhost:8700';
  const USERNAME = 'mateus';
  const PASSWORD = 'senha123';

  const fetchCliente = useCallback(async () => {
    setIsLoading(true);
    setError(null);

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

    try {
      const response = await fetch(`${API_URL}/clientes/`, { headers });
      if (!response.ok) throw new Error(`Erro na API: ${response.statusText}`);
      const data = await response.json();
      setClientes(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchCliente();
  }, [fetchCliente]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNovoCliente(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));
    headers.append('Content-Type', 'application/json');

    const url = modoEdicao
      ? `${API_URL}/clientes/${clienteEditandoId}`
      : `${API_URL}/clientes/`;
    const method = modoEdicao ? 'PUT' : 'POST';

    try {
      const response = await fetch(url, {
        method,
        headers,
        body: JSON.stringify({
          nome: novoCliente.nome,
          cpfOuCnpj: novoCliente.cpfOuCnpj.replace(/\D/g, ''),
          telefone: novoCliente.telefone.replace(/\D/g, ''),
          email: novoCliente.email,
          endereco: novoCliente.endereco,
        }),
      });

      if (!response.ok) throw new Error('Erro ao salvar cliente.');

      setNovoCliente({
        nome: '',
        telefone: '',
        email: '',
        endereco: '',
        cpfOuCnpj: '',
      });
      setModoEdicao(false);
      setClienteEditandoId(null);
      setShowForm(false);
      fetchCliente();
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const editarCliente = (cliente) => {
    setNovoCliente({
      nome: cliente.nome,
      telefone: cliente.telefone,
      email: cliente.email,
      endereco: cliente.endereco,
      cpfOuCnpj: cliente.cpfOuCnpj,
    });
    setClienteEditandoId(cliente.id);
    setModoEdicao(true);
    setShowForm(true);
  };

  const deletarCliente = async (id) => {
    if (!window.confirm('Deseja excluir este cliente?')) return;

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

    try {
      const res = await fetch(`${API_URL}/clientes/${id}`, {
        method: 'DELETE',
        headers,
      });
      if (!res.ok) throw new Error('Erro ao deletar cliente');
      fetchCliente();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="space-y-8">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-800">Meus Clientes</h1>
          <p className="text-gray-500 mt-1">Adicione, edite e visualize os clientes do seu sistema.</p>
        </div>
        <button
          onClick={() => {
            setShowForm(!showForm);
            setModoEdicao(false);
            setNovoCliente({ nome: '', telefone: '', email: '', endereco: '', cpfOuCnpj: '' });
          }}
          className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg flex items-center transition duration-300"
        >
          <PlusIcon />
          <span className="ml-2">{showForm ? 'Cancelar' : 'Novo cliente'}</span>
        </button>
      </div>

      {error && (
        <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded-md" role="alert">
          <p className="font-bold">Erro</p>
          <p>{error}</p>
        </div>
      )}

      {showForm && (
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">
            {modoEdicao ? 'Editar Cliente' : 'Novo cliente'}
          </h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              name="nome"
              value={novoCliente.nome}
              onChange={handleInputChange}
              placeholder="Neymar Jr"
              className="w-full p-2 border rounded-md bg-gray-50"
              required
            />

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <input
                type="tel"
                name="telefone"
                value={novoCliente.telefone}
                onChange={handleInputChange}
                placeholder="(11) 98765-4321"
                className="w-full p-2 border rounded-md bg-gray-50"
                required
              />
              <input
                type="email"
                name="email"
                value={novoCliente.email}
                onChange={handleInputChange}
                placeholder="neymar@email.com"
                className="w-full p-2 border rounded-md bg-gray-50"
                required
              />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <input
                type="text"
                name="endereco"
                value={novoCliente.endereco}
                onChange={handleInputChange}
                placeholder="Rua das Palmeiras, 123"
                className="w-full p-2 border rounded-md bg-gray-50"
                required
              />
              <input
                type="text"
                name="cpfOuCnpj"
                value={novoCliente.cpfOuCnpj}
                onChange={handleInputChange}
                placeholder="000.000.000-00 ou 00.000.000/0001-00"
                className="w-full p-2 border rounded-md bg-gray-50"
                required
              />
            </div>

            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-lg flex justify-center items-center transition-colors disabled:bg-green-300"
            >
              {isSubmitting ? <LoadingSpinner /> : modoEdicao ? 'Atualizar Cliente' : 'Salvar Cliente'}
            </button>
          </form>
        </div>
      )}

      <div className="bg-white p-6 rounded-lg shadow-md overflow-x-auto">
        <table className="min-w-full bg-white">
          <thead className="bg-gray-100">
            <tr>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Nome</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Telefone</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Email</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Endereço</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">CPF/CNPJ</th>
              <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Ações</th>
            </tr>
          </thead>
          <tbody className="text-gray-700">
            {isLoading ? (
              <tr><td colSpan="6" className="text-center py-4">A carregar clientes...</td></tr>
            ) : clientes.length > 0 ? (
              clientes.map(cliente => (
                <tr key={cliente.id} className="border-b border-gray-200 hover:bg-gray-50">
                  <td className="py-3 px-4 font-medium">{cliente.nome}</td>
                  <td className="py-3 px-4">{cliente.telefone}</td>
                  <td className="py-3 px-4 text-sm">{cliente.email}</td>
                  <td className="py-3 px-4 text-sm">{cliente.endereco}</td>
                  <td className="py-3 px-4 text-sm">{cliente.cpfOuCnpj}</td>
                  <td className="py-3 px-4 flex gap-2">
                    <button
                      onClick={() => editarCliente(cliente)}
                      className="text-blue-600 hover:underline"
                    ><button className="text-blue-500 hover:text-blue-700">
  <PencilIcon />
</button>
</button>
                    <button
                      onClick={() => deletarCliente(cliente.id)}
                      className="text-red-600 hover:underline"
                    ><button className="text-red-500 hover:text-red-700">
  <TrashIcon />
</button>
</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="6" className="text-center py-4">Nenhum cliente cadastrado.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClientesPage;
