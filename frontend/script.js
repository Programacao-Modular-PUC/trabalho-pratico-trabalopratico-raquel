const API = 'http://localhost:8080';
const resultadoTabela = document.getElementById('resultadoTabela');


document.addEventListener('DOMContentLoaded', () => {
    carregarDropdowns();
    configurarFormularioQuarto();
    configurarFiltroQuartosAluguel();
});

function formToObject(form) {
    const data = new FormData(form);
    const obj = {};
    for (const [key, value] of data.entries()) {
        obj[key] = value;
    }
    for (const input of form.querySelectorAll('input[type="checkbox"]')) {
        obj[input.name] = input.checked;
    }
    return obj;
}

async function enviar(url, metodo, body) {
    const resp = await fetch(`${API}${url}`, {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: body ? JSON.stringify(body) : undefined
    });
    
    const text = await resp.text();
    const json = text ? JSON.parse(text) : {};
    
    if (!resp.ok) {
        alert(`Erro: ${json.mensagem || 'Erro na requisição'}`);
        throw new Error(json.mensagem || 'Erro na requisição');
    }
    
    alert('Operação realizada com sucesso!');
    carregarDropdowns(); 
    return json;
}


function configurarFormularioQuarto() {
    document.getElementById('tipoQuarto').addEventListener('change', (e) => {
        document.querySelectorAll('.campo-extra').forEach(el => el.classList.add('hidden'));
        
        const tipo = e.target.value;
        if (tipo === 'individual') document.getElementById('camposIndividual').classList.remove('hidden');
        if (tipo === 'duplo') document.getElementById('camposDuplo').classList.remove('hidden');
        if (tipo === 'familia') document.getElementById('camposFamilia').classList.remove('hidden');
    });
}


function configurarFiltroQuartosAluguel() {
    document.getElementById('selectAluguelResidencia').addEventListener('change', async (e) => {
        const residenciaId = e.target.value;
        const selectQuarto = document.getElementById('selectAluguelQuarto');
        
        selectQuarto.disabled = false;
        selectQuarto.innerHTML = '<option value="" disabled selected>Carregando quartos...</option>';
        
        try {
            const resp = await fetch(`${API}/quartos`);
            if (!resp.ok) throw new Error();
            const quartos = await resp.json();
            
            const quartosFiltrados = quartos.filter(q => q.residenciaId == residenciaId || (q.residencia && q.residencia.id == residenciaId));
            
            selectQuarto.innerHTML = '<option value="" disabled selected>Selecione o Quarto...</option>';
            if(quartosFiltrados.length === 0) {
                selectQuarto.innerHTML = '<option value="" disabled>Nenhum quarto cadastrado nesta residência</option>';
                return;
            }
            
            quartosFiltrados.forEach(q => {
                selectQuarto.innerHTML += `<option value="${q.id}">Quarto ID #${q.id} (${q.tipo || 'N/A'}) - R$ ${q.valorBase}</option>`;
            });
        } catch (err) {
            selectQuarto.innerHTML = '<option value="" disabled>Erro ao carregar quartos</option>';
        }
    });
}


async function carregarDropdowns() {
    try {
        
        const resClientes = await fetch(`${API}/clientes`);
        if (resClientes.ok) {
            const clientes = await resClientes.json();
            const selectAluguelCli = document.getElementById('selectAluguelCliente');
            selectAluguelCli.innerHTML = '<option value="" disabled selected>Selecione o Cliente...</option>';
            clientes.forEach(c => {
                selectAluguelCli.innerHTML += `<option value="${c.id}">${c.nome} (CPF: ${c.cpf})</option>`;
            });
        }

        
        const resResidencias = await fetch(`${API}/residencias`);
        if (resResidencias.ok) {
            const residencias = await resResidencias.json();
            
            const selectReqQuarto = document.getElementById('selectResidenciaQuarto');
            const selectAluguelRes = document.getElementById('selectAluguelResidencia');
            
            selectReqQuarto.innerHTML = '<option value="" disabled selected>Selecione a Residência...</option>';
            selectAluguelRes.innerHTML = '<option value="" disabled selected>Selecione a Residência...</option>';
            
            residencias.forEach(r => {
                const exibicao = `${r.endereco}, Nº ${r.numero || 'S/N'} - ${r.bairro || ''}`;
                selectReqQuarto.innerHTML += `<option value="${r.id}">${exibicao}</option>`;
                selectAluguelRes.innerHTML += `<option value="${r.id}">${exibicao}</option>`;
            });
        }

        
        const resAlugueis = await fetch(`${API}/alugueis`);
        if (resAlugueis.ok) {
            const alugueis = await resAlugueis.json();
            const selectPagamento = document.getElementById('selectPagamentoAluguel');
            selectPagamento.innerHTML = '<option value="" disabled selected>Selecione o Aluguel...</option>';
            alugueis.forEach(a => {
                selectPagamento.innerHTML += `<option value="${a.id}">Reserva #${a.id} (Hóspedes: ${a.quantidadeHospedes})</option>`;
            });
        }
    } catch (error) {
        console.error('Erro ao sincronizar listas de seleção dinâmicas:', error);
    }
}


document.getElementById('formCliente').addEventListener('submit', async e => {
    e.preventDefault();
    await enviar('/clientes', 'POST', formToObject(e.target));
    e.target.reset();
});

document.getElementById('formResidencia').addEventListener('submit', async e => {
    e.preventDefault();
    await enviar('/residencias', 'POST', formToObject(e.target));
    e.target.reset();
});

document.getElementById('formQuarto').addEventListener('submit', async e => {
    e.preventDefault();
    const dados = formToObject(e.target);
    const tipo = dados.tipo;
    const residenciaId = dados.residenciaId;
    
    const quarto = {
        valorBase: Number(dados.valorBase),
        possuiAr: dados.possuiAr,
        possuiHidro: dados.possuiHidro
    };
    
    if (tipo === 'individual') quarto.quantidadeCamasSolteiro = Number(dados.quantidadeCamasSolteiro || 1);
    if (tipo === 'duplo') {
        quarto.tipoCama = dados.tipoCama;
        quarto.permiteBerco = dados.permiteBerco;
    }
    if (tipo === 'familia') {
        quarto.camasSolteiro = Number(dados.camasSolteiro || 2);
        quarto.camasCasal = Number(dados.camasCasal || 1);
        quarto.camasQueenKing = Number(dados.camasQueenKing || 0);
        quarto.quantidadeAmbientes = Number(dados.quantidadeAmbientes || 1);
    }
    
    await enviar(`/quartos/${tipo}?residenciaId=${residenciaId}`, 'POST', quarto);
    e.target.reset();
    document.querySelectorAll('.campo-extra').forEach(el => el.classList.add('hidden'));
});

document.getElementById('formAluguel').addEventListener('submit', async e => {
    e.preventDefault();
    const dados = formToObject(e.target);
    const aluguel = {
        dataEntrada: `${dados.dataEntrada}:00`,
        dataSaida: `${dados.dataSaida}:00`,
        quantidadeHospedes: Number(dados.quantidadeHospedes),
        solicitaBerco: dados.solicitaBerco
    };
    const url = `/alugueis?clienteId=${dados.clienteId}&residenciaId=${dados.residenciaId}&quartoId=${dados.quartoId}&tarifa=${dados.tarifa}`;
    await enviar(url, 'POST', aluguel);
    e.target.reset();
    document.getElementById('selectAluguelQuarto').disabled = true;
});

document.getElementById('formPagamento').addEventListener('submit', async e => {
    e.preventDefault();
    const dados = formToObject(e.target);
    await enviar(`/pagamentos/aluguel/${dados.aluguelId}?formaPagamento=${dados.formaPagamento}`, 'POST');
    e.target.reset();
});


async function buscarDados(recurso, filtro = '') {
    resultadoTabela.innerHTML = '<p class="placeholder-text">Buscando dados no sistema...</p>';
    
    try {
        let url = `${API}/${recurso}`;
        const resp = await fetch(url);
        
   
        if (!resp.ok) {
            const erroTexto = await resp.text();
            throw new Error(`Erro ${resp.status} do Servidor: ${erroTexto || 'Sem detalhes fornecidos pelo backend.'}`);
        }
        
        
        const texto = await resp.text();
        if (!texto) {
            resultadoTabela.innerHTML = '<p class="placeholder-text">A busca retornou vazia (204 No Content).</p>';
            return;
        }
        
        let dados = JSON.parse(texto);
        
        
        if (dados.content && Array.isArray(dados.content)) {
            dados = dados.content;
        }
        
        let lista = Array.isArray(dados) ? dados : [dados];
        
       
        if (filtro) {
            if (recurso === 'quartos') {
                lista = lista.filter(q => q.residenciaId == filtro || (q.residencia && q.residencia.id == filtro));
            }
            if (recurso === 'alugueis') {
                lista = lista.filter(a => a.clienteId == filtro || (a.cliente && a.cliente.id == filtro));
            }
        }
        
        
        if (lista.length === 0 || Object.keys(lista[0]).length === 0) {
            resultadoTabela.innerHTML = '<p class="placeholder-text">Nenhum registro encontrado para esta busca.</p>';
            return;
        }
        
        construirTabelaHTML(lista);
    } catch (err) {
        console.error(err);
        
        resultadoTabela.innerHTML = `
            <div style="padding: 20px; color: #991b1b; background-color: #fee2e2; border: 1px solid #f87171; border-radius: 8px;">
                <h4 style="margin-top:0;">Ocorreu um problema ao buscar os dados:</h4>
                <p style="margin-bottom:0;">${err.message}</p>
            </div>
        `;
    }
}


function formatarDado(valor) {
    if (valor === null || valor === undefined) return '-';
    

    if (typeof valor === 'boolean') return valor ? 'Sim' : 'Não';
    
  
    if (Array.isArray(valor)) {
        return `[ ${valor.length} registros ]`;
    }
    
  
    if (typeof valor === 'object') {
       
        if (valor.nome) return `${valor.nome} (ID: ${valor.id})`;
        if (valor.endereco) return `${valor.endereco}, ${valor.numero || 'S/N'}`;
        if (valor.id) return `ID: ${valor.id}`;
        
        return 'Ver detalhes'; 
    }
    
    return valor;
}


function construirTabelaHTML(lista) {
    const colunas = Object.keys(lista[0]);
    let html = '<table><thead><tr>';
    
   
    colunas.forEach(col => {
        html += `<th>${col.toUpperCase()}</th>`;
    });
    html += '</tr></thead><tbody>';
    
        lista.forEach(item => {
        html += '<tr>';
        colunas.forEach(col => {
         
            const celula = formatarDado(item[col]);
            html += `<td>${celula}</td>`;
        });
        html += '</tr>';
    });
    
    html += '</tbody></table>';
    resultadoTabela.innerHTML = html;
}