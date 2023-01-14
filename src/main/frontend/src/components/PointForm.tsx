import { useState } from "react";
import { CompoundPointRequest } from "../utils/ApiClient";
import { Button } from "./Button";
import CheckboxList from "./CheckboxList";
import TextInput from "./TextInput";
import { ReactComponent as Loader } from '../images/loader.svg';

interface PointFormProps {
    showLoader: boolean
    onSubmit(request: CompoundPointRequest): void,
    setGlobalR(r:number):void
};

export default function PointForm({ showLoader, onSubmit, setGlobalR }: PointFormProps) {
    const [xs, setXs] = useState<number[]>([]);
    const [y, setY] = useState('');
    const [rs, setRs] = useState<number[]>([]);

    const xWarning = (xs.length === 0 ? 'Выберите X' : '');
    const yWarning = (y.match(/^[+-]?[0-9]+(?:\.[0-9]+)?$/) === null || isNaN(parseFloat(y)) || parseFloat(y) > 3 || parseFloat(y) < -3 ? 'Введите Y' : '');
    const rWarning = (rs.length === 0 ? 'Выберите R' : '')
                   + (rs.filter(x => x <= 0).length > 0 ? 'R должен быть больше 0!' : '');

    const disableButton = xWarning !== '' || yWarning !== '' || rWarning !== '';
    const xValues: number[] = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
    const rValues: number[] = [0.5, 1, 1.5, 2];

    return (
        <div className="bg-gray-100 w-[90%] p-3 mx-auto mb-5 rounded-xl shadow-xl md:max-w-sm">
            <h1 className='font-bold text-lg'>Значение X:</h1>
            <CheckboxList
                name='x'
                options={xValues}
                selectedOptions={xs}
                warning={xWarning}
                setValues={setXs}
                disabled={showLoader}
            />
            <h1 className='font-bold text-lg'>Значение Y:</h1>
            <div className='px-3'>
                <TextInput 
                    name='y'
                    id='r-input'
                    maxLength={15}
                    placeholder="[-3..3]"
                    value={y}
                    onChange={(e) => setY(e.target.value)}
                    warningText={yWarning}
                    disabled={showLoader}
                />
            </div>
            <h1 className='font-bold text-lg'>Значение R:</h1>
            <CheckboxList
                name='r'
                options={rValues}
                selectedOptions={rs}
                warning={rWarning}
                setValues={(v) => {
                    setRs(v);
                    if (v.length === 0) setGlobalR(0);
                    else setGlobalR(Math.max(...v));
                }}
                disabled={showLoader}
            />
            <Button disabled={disableButton || showLoader} onClick={() => onSubmit({x:xs, r:rs, y: [parseFloat(y)]})}>
                { showLoader ? <Loader className='m-auto max-h-[1em]'/> : 'Бахнуть!' }
            </Button>
        </div>
    )
}