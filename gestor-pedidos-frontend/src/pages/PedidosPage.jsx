import React, { useState, useEffect, useCallback } from 'react';
import { PlusIcon, LoadingSpinner, TrashIcon } from '../components/icons';

const PedidosPage = () => {
    const [pedidos, setPedidos] = useState([]);
    const [clientes, setClientes] = useState([]);
    const [vendedores, setVendedores] = useState([]);
    const [produtos, setProdutos] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [novoPedido, setNovoPedido] = useState({
        dataEntrega: '',
        clienteId: '',
        vendedorId: '',
        itens: [{ produtoId: '', quantidade: 1 }]
    });

    const API_URL = 'http://localhost:8700';
    const USERNAME = 'mateus';
    const PASSWORD = 'senha123';

    const fetchData = useCallback(async () => {
        setIsLoading(true);
        setError(null);
        const headers = new Headers();
        headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));

        try {
            const [pedidosRes, clientesRes, vendedoresRes, produtosRes] = await Promise.all([
                fetch(`${API_URL}/pedidos/`, { headers }),
                fetch(`${API_URL}/clientes/`, { headers }),
                fetch(`${API_URL}/vendedores/`, { headers }),
                fetch(`${API_URL}/produtos/`, { headers }),
            ]);

            if (!pedidosRes.ok || !clientesRes.ok || !vendedoresRes.ok || !produtosRes.ok) {
                throw new Error('Falha ao buscar dados da API. Verifique se todos os serviços estão a rodar.');
            }

            const pedidosData = await pedidosRes.json();
            const clientesData = await clientesRes.json();
            const vendedoresData = await vendedoresRes.json();
            const produtosData = await produtosRes.json();

            setPedidos(pedidosData);
            setClientes(clientesData);
            setVendedores(vendedoresData);
            setProdutos(produtosData);

            // Lógica do vendedor: se só existir um, pré-seleciona-o.
            if (vendedoresData.length === 1) {
                setNovoPedido(prev => ({ ...prev, vendedorId: vendedoresData[0].id }));
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
        setNovoPedido(prevState => ({ ...prevState, [name]: value }));
    };
    
    const handleItemChange = (index, field, value) => {
        const newItens = [...novoPedido.itens];
        newItens[index][field] = value;
        setNovoPedido(prev => ({ ...prev, itens: newItens }));
    };

    const handleAddItem = () => {
        setNovoPedido(prev => ({
            ...prev,
            itens: [...prev.itens, { produtoId: '', quantidade: 1 }]
        }));
    };

    const handleRemoveItem = (index) => {
        const newItens = novoPedido.itens.filter((_, i) => i !== index);
        setNovoPedido(prev => ({ ...prev, itens: newItens }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setError(null);

        const headers = new Headers();
        headers.append('Authorization', 'Basic ' + btoa(USERNAME + ":" + PASSWORD));
        headers.append('Content-Type', 'application/json');
        
        const payload = {
            dataEntrega: novoPedido.dataEntrega,
            cliente: { id: novoPedido.clienteId },
            vendedor: novoPedido.vendedorId ? { id: novoPedido.vendedorId } : null,
            itens: novoPedido.itens.map(item => ({
                produtoId: item.produtoId,
                quantidade: parseInt(item.quantidade, 10)
            }))
        };

        try {
            const response = await fetch(`${API_URL}/pedidos`, {
                method: 'POST',
                headers,
                body: JSON.stringify(payload),
            });
            if (!response.ok) throw new Error('Falha ao cadastrar o pedido.');
            
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
                <h1 className="text-3xl font-bold text-gray-800">Pedidos</h1>
                <button onClick={() => setShowForm(!showForm)} className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg flex items-center">
                    <PlusIcon /> <span className="ml-2">{showForm ? 'Cancelar' : 'Novo Pedido'}</span>
                </button>
            </div>

            {error && <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded-md"><p>{error}</p></div>}

            {showForm && (
                <div className="bg-white p-6 rounded-lg shadow-md">
                    <h2 className="text-2xl font-semibold text-gray-700 mb-4">Novo Pedido</h2>
                    <form onSubmit={handleSubmit} className="space-y-6">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Cliente</label>
                                <select name="clienteId" value={novoPedido.clienteId} onChange={handleInputChange} className="w-full p-2 border rounded-md bg-gray-50" required>
                                    <option value="" disabled>Selecione um cliente</option>
                                    {clientes.map(c => <option key={c.id} value={c.id}>{c.nome}</option>)}
                                </select>
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Vendedor</label>
                                <select name="vendedorId" value={novoPedido.vendedorId} onChange={handleInputChange} className="w-full p-2 border rounded-md bg-gray-50" disabled={vendedores.length === 0}>
                                    <option value="">{vendedores.length === 0 ? 'Nenhum vendedor cadastrado' : 'Opcional'}</option>
                                    {vendedores.map(v => <option key={v.id} value={v.id}>{v.nome}</option>)}
                                </select>
                            </div>
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Data de Entrega</label>
                                <input type="date" name="dataEntrega" value={novoPedido.dataEntrega} onChange={handleInputChange} className="w-full p-2 border rounded-md bg-gray-50" required />
                            </div>
                        </div>

                        <h3 className="text-xl font-semibold text-gray-700 pt-4 border-t">Itens do Pedido</h3>
                        <div className="space-y-4">
                            {novoPedido.itens.map((item, index) => (
                                <div key={index} className="flex items-center space-x-4">
                                    <select value={item.produtoId} onChange={e => handleItemChange(index, 'produtoId', e.target.value)} className="w-1/2 p-2 border rounded-md bg-gray-50" required>
                                        <option value="" disabled>Selecione um produto</option>
                                        {produtos.map(p => <option key={p.id} value={p.id}>{p.nome} - R$ {p.valor.toFixed(2)}</option>)}
                                    </select>
                                    <input type="number" value={item.quantidade} onChange={e => handleItemChange(index, 'quantidade', e.target.value)} min="1" className="w-1/4 p-2 border rounded-md bg-gray-50" required />
                                    <button type="button" onClick={() => handleRemoveItem(index)} className="text-red-500 hover:text-red-700 p-2 rounded-full hover:bg-red-100"><TrashIcon /></button>
                                </div>
                            ))}
                        </div>
                        <button type="button" onClick={handleAddItem} className="text-blue-600 font-semibold flex items-center"><PlusIcon /> <span className="ml-2">Adicionar Item</span></button>
                        
                        <div className="flex justify-end space-x-4 pt-4 border-t">
                            <button type="button" onClick={() => setShowForm(false)} className="bg-gray-200 hover:bg-gray-300 text-gray-800 font-bold py-2 px-6 rounded-lg">Cancelar</button>
                            <button type="submit" disabled={isSubmitting} className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-6 rounded-lg flex justify-center items-center">{isSubmitting ? <LoadingSpinner /> : 'Salvar Pedido'}</button>
                        </div>
                    </form>
                </div>
            )}

            <div className="bg-white p-6 rounded-lg shadow-md overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead className="bg-gray-100">
                        <tr>
                            <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Nº Pedido</th>
                            <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Cliente</th>
                            <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Vendedor</th>
                            <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Data de Emissão</th>
                            <th className="text-left py-3 px-4 uppercase font-semibold text-sm text-gray-600">Status</th>
                        </tr>
                    </thead>
                    <tbody className="text-gray-700">
                        {isLoading ? (
                            <tr><td colSpan="5" className="text-center py-4">A carregar pedidos...</td></tr>
                        ) : pedidos.map(p => (
                            <tr key={p.id} className="border-b border-gray-200 hover:bg-gray-50">
                                <td className="py-3 px-4 font-mono">{p.numeroPedido}</td>
                                <td className="py-3 px-4">{p.cliente?.nome}</td>
                                <td className="py-3 px-4">{p.vendedor?.nome || 'N/A'}</td>
                                <td className="py-3 px-4">{p.dataEmissao}</td>
                                <td className="py-3 px-4">
                                    <span className={`px-2 py-1 text-xs font-semibold rounded-full ${p.situacao === 'ENTREGUE' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'}`}>
                                        {p.situacao}
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default PedidosPage;
