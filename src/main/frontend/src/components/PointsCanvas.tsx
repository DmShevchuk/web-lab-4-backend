import { useEffect, useRef, useState } from "react"
import { useAppDispatch } from "../app/hooks";
import { infoToast } from "../features/toasts/toastsSlice";
import { PointAttempt } from "../utils/ApiClient";

const canvasSize = 300;
const hitColor = 'rgb(125,246,92)';
const missColor = 'rgb(239, 68, 68)';

interface PointsCanvasProps {
    points: PointAttempt[],
    r: number,
    disabled: boolean,
    onClick(x:number, y:number):void
}



function renderGraph(ctx: CanvasRenderingContext2D, points:PointAttempt[], r: number) {
    ctx.clearRect(0, 0, canvasSize, canvasSize);

    ctx.strokeStyle = '#000';
    ctx.fillStyle = '#6c90ff';

    // Треугольник
    ctx.beginPath();
    ctx.moveTo(canvasSize / 2, canvasSize / 2);
    ctx.lineTo(canvasSize / 3, canvasSize / 2);
    ctx.lineTo(canvasSize / 2, (canvasSize / 2) - (((canvasSize / 2) / 3) * 2));
    ctx.fill();

// Круг
    ctx.beginPath();
    ctx.arc(canvasSize / 2, canvasSize / 2, canvasSize / 6, Math.PI / 2, - Math.PI, false);
    ctx.lineTo(canvasSize / 2, canvasSize / 2)
    ctx.fill();

// Прямоугольник
    ctx.beginPath();
    ctx.rect(canvasSize / 2, canvasSize / 2, canvasSize / 6, canvasSize / 3);
    ctx.fill();

    ctx.fillStyle = '#000';

    ctx.beginPath();
    ctx.moveTo(0, canvasSize/2);
    ctx.lineTo(canvasSize, canvasSize/2);
    ctx.lineTo(canvasSize-10, canvasSize/2-10);
    ctx.moveTo(canvasSize, canvasSize/2);
    ctx.lineTo(canvasSize-10,canvasSize/2+10);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(canvasSize/2, canvasSize);
    ctx.lineTo(canvasSize/2, 0);
    ctx.lineTo(canvasSize/2-10, 10);
    ctx.moveTo(canvasSize/2, 0);
    ctx.lineTo(canvasSize/2+10, 10);
    ctx.stroke();

    const labels = ['-R', '-R/2', '', 'R/2', 'R'];

    // Draw axes labels
    for (let i=1; i<6; i++) {
        ctx.beginPath();
        ctx.moveTo(i*canvasSize/6, canvasSize/2-5);
        ctx.lineTo(i*canvasSize/6, canvasSize/2+5);
        ctx.moveTo(canvasSize/2-5, i*canvasSize/6);
        ctx.lineTo(canvasSize/2+5, i*canvasSize/6);
        ctx.stroke();

        ctx.textAlign = 'center';
        ctx.textBaseline = 'bottom';
        ctx.fillText(labels[i-1], i*canvasSize/6, canvasSize/2-7);

        ctx.textAlign = 'left';
        ctx.textBaseline = 'middle';
        ctx.fillText(labels[i-1], canvasSize/2+7, canvasSize - i*canvasSize/6);
    }

    if (r <= 0) return;

    // Draw all points
    points.forEach((v) => {
        const x = v.x / r * canvasSize / 3 + canvasSize / 2;
        const y = -v.y / r * canvasSize / 3 + canvasSize / 2;

        ctx.fillStyle = (v.success ? hitColor : missColor);
        ctx.beginPath();
        ctx.arc(x, y, 3, 0, Math.PI * 2);
        ctx.fill();
    });
}

export default function PointsCanvas({points, r, disabled, onClick} : PointsCanvasProps) {
    const [areasImage] = useState('');
    const dispatch = useAppDispatch();
    const canvasRef = useRef<HTMLCanvasElement>(null);

    useEffect(() => {
        if (canvasRef.current !== null) {
            const ctx = canvasRef.current.getContext('2d');
            if (ctx !== null) renderGraph(ctx, points, r);
        }
    }, [points, r, canvasRef]);

    return (
        <canvas
            ref={canvasRef} 
            style={{background: `url("${areasImage}")`}}
            className={(disabled ? 'blur-sm' : '')}
            width={canvasSize}
            height={canvasSize}
            onClick={(e) => {
                if (!disabled) {
                    if (r <= 0) {
                        dispatch(infoToast('Для начала выберите R!'));
                    } else {
                        const offsetX = e.nativeEvent.offsetX;
                        const offsetY = e.nativeEvent.offsetY;

                        let x = (2 * offsetX / canvasSize - 1) *  1.5 * r;
                        let y = (2 * offsetY / canvasSize - 1) * -1.5 * r;
                        x = Math.round(x * 100) / 100;
                        y = Math.round(y * 100) / 100;

                        onClick(x, y);
                    }
                }
            }}
        />
    )
}