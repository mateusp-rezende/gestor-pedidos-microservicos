import React, { useState, useEffect, useCallback } from 'react';
import { PlusIcon, LoadingSpinner,TrashIcon,PencilIcon } from '../components/icons';

// --- Componente da Página de Produtos ---
const ProdutosPage = () => {
  const [produtos, setProdutos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [modoEdicao, setModoEdicao] = useState(false);
  const [produtoEditandoId, setProdutoEditandoId] = useState(null);

  const [novoProduto, setNovoProduto] = useState({
    nome: '',
    tipo: 'PRODUTO',
    unidadeMedida: 'UN',
    valor: '',
    precoDeCusto: '',
    descricao: ''
  });

  const API_URL = 'http://localhost:8700'; 
  const USERNAME = 'mateus';
  const PASSWORD = 'senha123';

  const fetchProdutos = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    
    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

    try {
      const response = await fetch(`${API_URL}/produtos/`, { headers });
      if (!response.ok) throw new Error(`Erro na API: ${response.statusText}`);
      const data = await response.json();
      setProdutos(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProdutos();
  }, [fetchProdutos]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNovoProduto(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));
    headers.append('Content-Type', 'application/json');

    const url = modoEdicao
      ? `${API_URL}/produtos/${produtoEditandoId}`
      : `${API_URL}/produtos/`;
    const method = modoEdicao ? 'PUT' : 'POST';

    try {
      const response = await fetch(url, {
        method,
        headers,
        body: JSON.stringify({
          ...novoProduto,
          valor: parseFloat(novoProduto.valor) || 0,
          precoDeCusto: parseFloat(novoProduto.precoDeCusto) || 0
        }),
      });

      if (!response.ok) throw new Error('Falha ao salvar o produto.');
      
      setNovoProduto({ nome: '', tipo: 'PRODUTO', unidadeMedida: 'UN', valor: '', precoDeCusto: '', descricao: '' });
      setShowForm(false);
      setModoEdicao(false);
      setProdutoEditandoId(null);
      fetchProdutos();
    } catch (err) {
      setError(err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  // Função para iniciar edição de produto
  const editarProduto = (produto) => {
    setNovoProduto({
      nome: produto.nome,
      tipo: produto.tipo || 'PRODUTO',
      unidadeMedida: produto.unidadeMedida || 'UN',
      valor: produto.valor,
      precoDeCusto: produto.precoDeCusto,
      descricao: produto.descricao || ''
    });
    setProdutoEditandoId(produto.id);
    setModoEdicao(true);
    setShowForm(true);
  };

  // Função para deletar produto
  const deletarProduto = async (id) => {
    if (!window.confirm('Deseja realmente excluir este produto?')) return;

    const headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

    try {
      const res = await fetch(`${API_URL}/produtos/${id}`, {
        method: 'DELETE',
        headers,
      });
      if (!res.ok) throw new Error('Erro ao deletar produto');
      fetchProdutos();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="space-y-8">
        <div className="flex justify-between items-center">
            <div>
                <h1 className="text-3xl font-bold text-gray-800">Catálogo de Produtos</h1>
                <p className="text-gray-500 mt-1">Adicione, edite e visualize os produtos do seu sistema.</p>
            </div>
            <button
              onClick={() => {
                setShowForm(!showForm);
                setModoEdicao(false);
                setNovoProduto({ nome: '', tipo: 'PRODUTO', unidadeMedida: 'UN', valor: '', precoDeCusto: '', descricao: '' });
              }}
              className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg flex items-center transition duration-300"
            >
              <PlusIcon />
              <span className="ml-2">{showForm ? 'Cancelar' : (modoEdicao ? 'Editar Produto' : 'Novo Produto')}</span>
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
              <h2 className="text-2xl font-semibold text-gray-700 mb-4">{modoEdicao ? 'Editar Produto' : 'Novo Produto'}</h2>
              <form onSubmit={handleSubmit} className="space-y-4">
                <input type="text" name="nome" value={novoProduto.nome} onChange={handleInputChange} placeholder="Nome do Produto" className="w-full p-2 border rounded-md bg-gray-50" required />
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input type="number" name="valor" value={novoProduto.valor} onChange={handleInputChange} placeholder="Preço de Venda (ex: 25.50)" className="w-full p-2 border rounded-md bg-gray-50" required step="0.01" />
                    <input type="number" name="precoDeCusto" value={novoProduto.precoDeCusto} onChange={handleInputChange} placeholder="Preço de Custo (ex: 15.00)" className="w-full p-2 border rounded-md bg-gray-50" step="0.01" />
                </div>
                <textarea name="descricao" value={novoProduto.descricao} onChange={handleInputChange} placeholder="Descrição do Produto" className="w-full p-2 border rounded-md bg-gray-50"></textarea>
                <button type="submit" disabled={isSubmitting} className="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded-lg flex justify-center items-center transition-colors disabled:bg-green-300">
                  {isSubmitting ? <LoadingSpinner /> : modoEdicao ? 'Atualizar Produto' : 'Salvar Produto'}
                </button>
              </form>
            </div>
        )}

        <div className="bg-white p-6 rounded-lg shadow-md overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead className="bg-gray-100">
              <tr>
                <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Nome</th>
                <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Preço de Venda</th>
                <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Descrição</th>
                <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Ações</th>
              </tr>
            </thead>
            <tbody className="text-gray-700">
              {isLoading ? (
                <tr><td colSpan="4" className="text-center py-4">A carregar produtos...</td></tr>
              ) : produtos.length > 0 ? (
                produtos.map(produto => (
                  <tr key={produto.id} className="border-b border-gray-200 hover:bg-gray-50">
                    <td className="py-3 px-4 font-medium">{produto.nome}</td>
                    <td className="py-3 px-4">R$ {produto.valor ? parseFloat(produto.valor).toFixed(2) : '0.00'}</td>
                    <td className="py-3 px-4 text-sm">{produto.descricao}</td>
                    <td className="py-3 px-4 flex gap-2">
                      <button
                        onClick={() => editarProduto(produto)}
                        className="text-blue-600 hover:underline"
                      ><button className="text-blue-500 hover:text-blue-700">
  <PencilIcon />
</button>
</button>
                      <button
                        onClick={() => deletarProduto(produto.id)}
                        className="text-red-600 hover:underline"
                      ><button className="text-red-500 hover:text-red-700">
  <TrashIcon />
</button>
</button>
                    </td>
                  </tr>
                ))
              ) : (
                 <tr><td colSpan="4" className="text-center py-4">Nenhum produto cadastrado.</td></tr>
              )}
            </tbody>
          </table>
        </div>
    </div>
  );
};

export default ProdutosPage;
