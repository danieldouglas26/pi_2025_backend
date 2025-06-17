CREATE TABLE public.operacao_log (
    id SERIAL PRIMARY KEY,
    tabela_afetada VARCHAR(255) NOT NULL,
    operacao VARCHAR(10) NOT NULL, -- INSERT, UPDATE, DELETE
    id_registroLong NOT NULL,     -- ID do registro afetado
    dados_anteriores JSONB NULL,   -- Dados antes da operação (para UPDATE/DELETE)
    dados_novos JSONB NULL,        -- Dados após operação (para INSERT/UPDATE)
    data_hora TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    usuario VARCHAR(255) NULL      -- Poderia ser preenchido com current_user
);



CREATE OR REPLACE FUNCTION public.log_operacao()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO operacao_log (
            tabela_afetada, 
            operacao, 
            id_registro, 
            dados_novos,
            usuario
        ) VALUES (
            TG_TABLE_NAME, 
            TG_OP, 
            NEW.id, 
            to_jsonb(NEW),
            current_user
        );
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO operacao_log (
            tabela_afetada, 
            operacao, 
            id_registro, 
            dados_anteriores,
            dados_novos,
            usuario
        ) VALUES (
            TG_TABLE_NAME, 
            TG_OP, 
            NEW.id, 
            to_jsonb(OLD),
            to_jsonb(NEW),
            current_user
        );
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO operacao_log (
            tabela_afetada, 
            operacao, 
            id_registro, 
            dados_anteriores,
            usuario
        ) VALUES (
            TG_TABLE_NAME, 
            TG_OP, 
            OLD.id, 
            to_jsonb(OLD),
            current_user
        );
    END IF;
    
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;




-- Para tabela rota
CREATE TRIGGER trg_log_rota
AFTER INSERT OR UPDATE OR DELETE ON public.rota
FOR EACH ROW EXECUTE FUNCTION public.log_operacao();

-- Para tabela itinerario
CREATE TRIGGER trg_log_itinerario
AFTER INSERT OR UPDATE OR DELETE ON public.itinerario
FOR EACH ROW EXECUTE FUNCTION public.log_operacao();


-- Para tabela paradarota
CREATE TRIGGER trg_log_paradarota
AFTER INSERT OR UPDATE OR DELETE ON public.paradarota
FOR EACH ROW EXECUTE FUNCTION public.log_operacao();